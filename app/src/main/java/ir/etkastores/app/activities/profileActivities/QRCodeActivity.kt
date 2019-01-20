package ir.etkastores.app.activities.profileActivities

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.text.TextUtils
import android.widget.ImageView

import com.github.sumimakito.awesomeqr.option.RenderOption
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel

import butterknife.BindView
import butterknife.ButterKnife
import com.github.sumimakito.awesomeqr.AwesomeQrRenderer
import com.github.sumimakito.awesomeqr.RenderResult
import com.github.sumimakito.awesomeqr.option.color.Color
import io.michaelrocks.paranoid.Obfuscate
import ir.etkastores.app.EtkaApp
import ir.etkastores.app.R
import ir.etkastores.app.activities.BaseActivity
import ir.etkastores.app.adapters.recyclerViewAdapters.ProductsRecyclerAdapter
import ir.etkastores.app.data.ProfileManager
import ir.etkastores.app.models.ProductModel
import ir.etkastores.app.ui.dialogs.MessageDialog
import ir.etkastores.app.ui.views.EtkaToolbar
import kotlinx.android.synthetic.main.activity_qr_code_generated.*

@Obfuscate
class QRCodeActivity : BaseActivity(), EtkaToolbar.EtkaToolbarActionsListener{

    private var adapter: ProductsRecyclerAdapter? = null
    private var loadingDialog: AlertDialog? = null
    private var barcodeData : String? = null


    private var tempProductForDelete: ProductModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (ProfileManager.getInstance().isGuest) {
            finish()
            return
        }
        setContentView(R.layout.activity_qr_code_generated)
        ButterKnife.bind(this)
        qr_toolbar.setActionListeners(this)
        //toolbar!!.showBillButton(true)
        barcodeData = intent.getStringExtra("Barcode")
        initViews()
    }

    override fun onResume() {
        super.onResume()
        EtkaApp.getInstance().screenView("Next Shopping List Activity")
    }

    private fun initViews() {
        val renderOption = RenderOption()
        renderOption.content = barcodeData ?: "" // content to encode
        renderOption.size = 800 // size of the final QR code image
        renderOption.borderWidth = 20 // width of the empty space around the QR code
        renderOption.ecl = ErrorCorrectionLevel.M // (optional) specify an error correction level
        renderOption.patternScale = 0.95f // (optional) specify a scale for patterns
        renderOption.roundedPatterns = false // (optional) if true, blocks will be drawn as dots instead
        renderOption.clearBorder = true // if set to true, the background will NOT be drawn on the border area
        renderOption.color = Color(false,ContextCompat.getColor(this,R.color.white),ContextCompat.getColor(this,R.color.white),ContextCompat.getColor(this,R.color.black)) // set a color palette for the QR code
        //renderOption.setBackground(background); // set a background, keep reading to find more about it
        //renderOption.setLogo(logo); // set a logo, keep reading to find more about it

        val result = AwesomeQrRenderer.renderAsync(renderOption, { result ->
            if (result.bitmap != null) {
                this.runOnUiThread {
                    img_qr_code.setImageBitmap(result.bitmap!!)
                }
                // play with the bitmap
            } else if (result.type == RenderResult.OutputType.GIF) {
                // If your Background is a GifBackground, the image
                // will be saved to the output file set in GifBackground
                // instead of being returned here. As a result, the
                // result.bitmap will be null.
            } else {
                // Oops, something gone wrong.
            }
        }, {
            exception -> exception.printStackTrace()
            // Oops, something gone wrong.
        })
    }


    override fun onToolbarBackClick() {
        onBackPressed()
    }

    override fun onActionClick(actionCode: Int) {

    }




    private fun showLoadingErrorDialog(message: String?) {
        var msg = message
        if (TextUtils.isEmpty(msg))
            msg = resources.getString(R.string.errorInLoadingNextShoppingList)
        val messageDialog = MessageDialog.warningRetry(resources.getString(R.string.error), msg)
        messageDialog.show(supportFragmentManager, false, object : MessageDialog.MessageDialogCallbacks {
            override fun onDialogMessageButtonsClick(button: Int) {
                if (button == MessageDialog.MessageDialogCallbacks.RIGHT_BUTTON) {
                    initViews()
                } else {
                    if (adapter!!.itemCount == 0) {
                        finish()
                    }
                }
                messageDialog.dialog.cancel()
            }

            override fun onDialogMessageDismiss() {

            }
        })
    }

    private fun showDeleteErrorDialog(message: String?) {
        var msg = message
        if (TextUtils.isEmpty(msg))
            msg = resources.getString(R.string.errorInDeletingProductTryLater)
        val messageDialog = MessageDialog.warningRetry(resources.getString(R.string.error), msg)
        messageDialog.show(supportFragmentManager, false, object : MessageDialog.MessageDialogCallbacks {
            override fun onDialogMessageButtonsClick(button: Int) {
                if (button == MessageDialog.MessageDialogCallbacks.RIGHT_BUTTON) {
                    //deleteProductFromList(tempProductForDelete!!)
                } else {
                    if (adapter!!.itemCount == 0) {
                        finish()
                    }
                }
                messageDialog.dialog.cancel()
            }

            override fun onDialogMessageDismiss() {

            }
        })
    }

    /*private fun showLoading() {
        if (adapter!!.itemCount == 0) circularProgress!!.visibility = View.VISIBLE
        linearProgress!!.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        linearProgress!!.visibility = View.GONE
        circularProgress!!.visibility = View.GONE
    }*/

}