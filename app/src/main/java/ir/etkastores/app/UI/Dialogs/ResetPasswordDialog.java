package ir.etkastores.app.UI.Dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

    @BindView(R.id.customerClubCardNumberInputHolder)
    View customerCardHolder;

    @BindView(R.id.mobilePhoneInputHolder)
    View mobileHolder;

    @BindView(R.id.emailInputHoler)
    View emailHolder;

    @BindView(R.id.customerClubCardNumberInput)
    EditText customeCard;

    @BindView(R.id.mobilePhoneInput)
    EditText mobilePhone;

    @BindView(R.id.emailAddressInput)
    EditText emailAddress;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_reset_password,container,false);
        ButterKnife.bind(this,view);

        return view;
    }

    @OnClick(R.id.sendButton)
    public void onSendClick(){

    }

    private void initEmailMode(){
        emailHolder.setVisibility(View.VISIBLE);
        customerCardHolder.setVisibility(View.GONE);
        mobilePhone.setVisibility(View.GONE);
    }

    private void initCustomerClubMode(){
        emailHolder.setVisibility(View.GONE);
        customerCardHolder.setVisibility(View.VISIBLE);
        mobilePhone.setVisibility(View.VISIBLE);
    }

}
