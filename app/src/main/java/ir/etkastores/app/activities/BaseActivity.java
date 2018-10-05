package ir.etkastores.app.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import ir.etkastores.app.BuildConfig;
import ir.etkastores.app.R;
import ir.etkastores.app.data.PushTokenManager;
import ir.etkastores.app.ui.Toaster;
import ir.etkastores.app.ui.dialogs.MessageDialog;
import ir.etkastores.app.utils.DiskDataHelper;
import ir.etkastores.app.utils.IntentHelper;
import ir.etkastores.app.utils.RootUtils;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by Sajad on 9/3/17.
 */

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawableResource(R.color.mainBackgroundColor);
        overridePendingTransition(0, 0);
        checkForceUpdate();
        checkRootedDevice();
    }

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(context));
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        PushTokenManager.getInstance().syncToken();
    }

    private void checkForceUpdate() {
        if (DiskDataHelper.getForceUpdateVersion() > BuildConfig.VERSION_CODE) {
            MessageDialog dialog = MessageDialog.forceUpdate();
            dialog.show(getSupportFragmentManager(), false, new MessageDialog.MessageDialogCallbacks() {
                @Override
                public void onDialogMessageButtonsClick(int button) {
                    if (button == RIGHT_BUTTON) {
                        IntentHelper.showWeb(BaseActivity.this, DiskDataHelper.getForceUpdateUrl());
                    } else {
                        FinishActivity.show(BaseActivity.this);
                        finish();
                    }
                }

                @Override
                public void onDialogMessageDismiss() {

                }
            });
        }
    }

    private void checkRootedDevice() {
        if (RootUtils.isRooted(this)) {
            Toaster.showLong(this, R.string.rootedDeviceCantUseApp);
            finish();
            return;
        }
    }

}
