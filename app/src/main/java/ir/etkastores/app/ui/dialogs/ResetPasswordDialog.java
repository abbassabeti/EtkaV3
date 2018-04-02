package ir.etkastores.app.ui.dialogs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.etkastores.app.R;

/**
 * Created by Sajad on 9/22/17.
 */

public class ResetPasswordDialog extends BaseDialog {

    public static final String TAG = "RESET_PASSWORD_DIALOG_FRAGMENT_TAG";

    @BindView(R.id.mobilePhoneInput)
    EditText mobilePhone;

    private OnUserEnterPhoneNumberToResetListener callback;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_reset_password,container,false);
        ButterKnife.bind(this,view);
        return view;
    }

    @OnClick(R.id.sendButton)
    public void onSendClick(){
        if (!isAdded()) return;
        if (callback != null) callback.onUserSetPhoneNumberToReset(mobilePhone.getText().toString());
    }

    public void show(FragmentManager fragmentManager,OnUserEnterPhoneNumberToResetListener callback){
        this.callback = callback;
        show(fragmentManager,TAG);
    }

    public interface OnUserEnterPhoneNumberToResetListener{
        void onUserSetPhoneNumberToReset(String phone);
    }

}
