package ir.etkastores.app.activities.profileActivities

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Base64
import android.util.Base64OutputStream
import android.view.View

import butterknife.ButterKnife
import io.fabric.sdk.android.services.network.HttpRequest
import io.michaelrocks.paranoid.Obfuscate
import ir.etkastores.app.EtkaApp
import ir.etkastores.app.R
import ir.etkastores.app.activities.BaseActivity
import ir.etkastores.app.activities.ProductActivity
import ir.etkastores.app.adapters.recyclerViewAdapters.ProductsRecyclerAdapter
import ir.etkastores.app.data.ProfileManager
import ir.etkastores.app.models.OauthResponse
import ir.etkastores.app.models.ProductModel
import ir.etkastores.app.ui.Toaster
import ir.etkastores.app.ui.dialogs.MessageDialog
import ir.etkastores.app.ui.views.EtkaToolbar
import ir.etkastores.app.utils.AESHelper.decryption
import ir.etkastores.app.utils.AESHelper.encryption
import ir.etkastores.app.utils.AdjustHelper
import ir.etkastores.app.utils.DialogHelper
import ir.etkastores.app.webServices.ApiProvider
import kotlinx.android.synthetic.main.activity_next_shopping_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.util.zip.Deflater
import java.util.zip.GZIPInputStream
import java.util.zip.GZIPOutputStream

@Obfuscate
class NextShoppingListActivity : BaseActivity(), EtkaToolbar.EtkaToolbarActionsListener, ProductsRecyclerAdapter.ProductsRecyclerCallbacks {


    private var savedProductsReq: Call<OauthResponse<List<ProductModel>>>? = null
    private var deleteProductReq: Call<OauthResponse<Long>>? = null
    private var adapter: ProductsRecyclerAdapter? = null
    private var loadingDialog: AlertDialog? = null


    private var tempProductForDelete: ProductModel? = null

    private var barcodeContent: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (ProfileManager.getInstance().isGuest) {
            finish()
            return
        }
        setContentView(R.layout.activity_next_shopping_list)
        ButterKnife.bind(this)
        nxtShopToolbar.setActionListeners(this)
        nxtShopToolbar.showBillButton(true)
        initViews()
    }

    override fun onResume() {
        super.onResume()
        EtkaApp.getInstance().screenView("Next Shopping List Activity")
    }

    private fun initViews() {
        adapter = ProductsRecyclerAdapter(this, this)
        adapter!!.setNextShoppingListMode(true)
        recyclerView.adapter = adapter
        loadProducts()
    }

    override fun onPause() {
        super.onPause()
        if (savedProductsReq != null) savedProductsReq!!.cancel()
        if (deleteProductReq != null) deleteProductReq!!.cancel()
    }

    override fun onToolbarBackClick() {
        onBackPressed()
    }

    override fun onActionClick(actionCode: Int) {
        if (actionCode == EtkaToolbar.EtkaToolbarActionsListener.BILL_BUTTON) {
            val intent = Intent(this, QRCodeActivity::class.java)
            if (barcodeContent != null) {
                intent.putExtra("Barcode", barcodeContent!!)
                this.startActivity(intent)
            }
        }
    }

    private fun showEmptyMessage() {
        messageView!!.show(R.drawable.ic_warning_orange_48dp, R.string.yourNextShoppingListIsEmpty, 0, null)
    }

    private fun loadProducts() {
        showLoading()
        savedProductsReq = ApiProvider.getInstance().authorizedApi.savedProducts
        savedProductsReq!!.enqueue(object : Callback<OauthResponse<List<ProductModel>>> {
            override fun onResponse(call: Call<OauthResponse<List<ProductModel>>>, response: Response<OauthResponse<List<ProductModel>>>) {
                if (isFinishing) return
                if (response.isSuccessful) {
                    if (response.body()!!.isSuccessful) {
                        adapter!!.addItems(response.body()!!.data)
                        val content = response.body()!!.data.map { it.barCode }.joinToString("-")
                        barcodeContent = compressString(content)
                        val value = barcodeContent ?: ""
                        val decomp = decompress(decryption(value))
                        if (adapter!!.itemCount == 0) showEmptyMessage()
                    } else {
                        showLoadingErrorDialog(response.body()!!.meta.message)
                    }
                } else {
                    onFailure(call, null)
                }
                hideLoading()
            }

            override fun onFailure(call: Call<OauthResponse<List<ProductModel>>>, throwable: Throwable?) {
                if (isFinishing) return
                hideLoading()
                showLoadingErrorDialog(null)
            }
        })
    }

    fun compressCodeContent(inputString : String): String{
        val input = inputString.toByteArray()

        // Compress the bytes
        val output = ByteArray(100)
        val compresser = Deflater()
        compresser.setInput(input)
        compresser.finish()
        val compressedDataLength = compresser.deflate(output)
        compresser.end()

        return String(output, Charsets.UTF_8)
    }

    override fun onLoadMore() {

    }

    override fun onProductItemClick(productModel: ProductModel) {
        AdjustHelper.sendAdjustEvent(AdjustHelper.OpenProductFromNextShoppingList)
        if (productModel.relatedProducts == null || productModel.relatedProducts.size == 0)
            productModel.relatedProducts = adapter!!.items
        ProductActivity.show(this, productModel)
    }

    override fun onProductSavedDeleteClick(productModel: ProductModel) {
        val messageDialog = MessageDialog.sureToDeleteProductFromNextShoppingList()
        messageDialog.show(supportFragmentManager, true, object : MessageDialog.MessageDialogCallbacks {
            override fun onDialogMessageButtonsClick(button: Int) {
                if (button == MessageDialog.MessageDialogCallbacks.RIGHT_BUTTON) {
                    deleteProductFromList(productModel)
                }
                messageDialog.dialog.cancel()
            }

            override fun onDialogMessageDismiss() {

            }
        })
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

    private fun deleteProductFromList(productModel: ProductModel) {
        AdjustHelper.sendAdjustEvent(AdjustHelper.DeleteFromNextShoppingList)
        tempProductForDelete = productModel
        loadingDialog = DialogHelper.showLoading(this@NextShoppingListActivity, R.string.inDeletingProductFromYourNextShoppingList)
        deleteProductReq = ApiProvider.getInstance().authorizedApi.deleteSavedProduct(productModel.id)
        deleteProductReq!!.enqueue(object : Callback<OauthResponse<Long>> {
            override fun onResponse(call: Call<OauthResponse<Long>>, response: Response<OauthResponse<Long>>) {
                if (isFinishing) return
                if (response.isSuccessful) {
                    if (response.isSuccessful) {
                        Toaster.show(this@NextShoppingListActivity, R.string.productDeletedSuccessfully)
                        adapter!!.deleteItem(adapter!!.items.indexOf(tempProductForDelete))
                        tempProductForDelete = null
                    } else {
                        showDeleteErrorDialog(response.body()!!.meta.message)
                    }
                } else {
                    onFailure(call, null)
                }
                loadingDialog!!.cancel()
            }

            override fun onFailure(call: Call<OauthResponse<Long>>, throwable: Throwable?) {
                if (isFinishing) return
                loadingDialog!!.cancel()
                showDeleteErrorDialog(null)
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
                    deleteProductFromList(tempProductForDelete!!)
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

    private fun showLoading() {
        if (adapter!!.itemCount == 0) circularProgress!!.visibility = View.VISIBLE
        linearProgress!!.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        linearProgress!!.visibility = View.GONE
        circularProgress!!.visibility = View.GONE
    }

    companion object {

        @Throws(IOException::class)
        fun compressString(srcTxt: String): String {
            val os = ByteArrayOutputStream(srcTxt.length)
            val gos = GZIPOutputStream(os)
            gos.write(srcTxt.toByteArray())
            gos.close()
            val compressed = os.toByteArray()
            os.close()
            return Base64.encodeToString(compressed,Base64.NO_WRAP)
        }

        @Throws(IOException::class)
        fun decompress(zipText: String): String {
            val compressed = Base64.decode(zipText,Base64.NO_WRAP)
            if (compressed.size > 4) {
                val gzipInputStream = GZIPInputStream(
                        ByteArrayInputStream(compressed))

                val baos = ByteArrayOutputStream()
                var value = 0
                while (value != -1) {
                    value = gzipInputStream.read()
                    if (value != -1) {
                        baos.write(value)
                    }
                }
                gzipInputStream.close()
                baos.close()
                return String(baos.toByteArray())
            } else {
                return ""
            }
        }

        fun show(activity: Activity) {
            val intent = Intent(activity, NextShoppingListActivity::class.java)
            activity.startActivity(intent)
        }
    }

}