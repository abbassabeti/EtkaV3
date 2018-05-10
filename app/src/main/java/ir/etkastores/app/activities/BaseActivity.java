package ir.etkastores.app.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import ir.etkastores.app.BuildConfig;
import ir.etkastores.app.R;
import ir.etkastores.app.ui.dialogs.MessageDialog;
import ir.etkastores.app.utils.DiskDataHelper;
import ir.etkastores.app.utils.IntentHelper;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static ir.etkastores.app.ui.dialogs.MessageDialog.MessageDialogCallbacks.RIGHT_BUTTON;

/**
 * Created by Sajad on 9/3/17.
 */

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawableResource(R.color.mainBackgeoundColor);
        overridePendingTransition(0, 0);
        checkForceUpdate();
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

}
