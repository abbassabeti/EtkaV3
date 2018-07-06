package ir.etkastores.app.ui.dialogs;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.etkastores.app.R;
import ir.etkastores.app.utils.DiskDataHelper;

public class HekmatCardLoginDialog extends BaseDialog {

    public static HekmatCardLoginDialog newInstance() {
        return new HekmatCardLoginDialog();
    }

    @BindView(R.id.hekmatCardNumberEt)
    AppCompatEditText hekmatCardNumberEt;

    @BindView(R.id.hekmatCardPasswordEt)
    AppCompatEditText hekmatCardPasswordEt;

    private OnHekmatCardCallbackListener callbackListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hekmat_card_login, container, false);
        ButterKnife.bind(this, view);
        String lastCardNumber = DiskDataHelper.getLastHekmatCardNumber();
        if (!TextUtils.isEmpty(lastCardNumber)) hekmatCardNumberEt.setText(lastCardNumber);
        return view;
    }

    public void show(FragmentManager fragmentManager, OnHekmatCardCallbackListener callbackListener) {
        this.callbackListener = callbackListener;
        show(fragmentManager, "HekmatCardLoginDialog");
    }

    @OnClick(R.id.enterButton)
    public void onEnterClick() {
        if (callbackListener != null)
            callbackListener.onHekmatCardLoginDialogSubmitButton(hekmatCardNumberEt.getText().toString(), hekmatCardPasswordEt.getText().toString());
    }

    @OnClick(R.id.registerButton)
    public void onRegisterClick(){
        if (callbackListener != null)
            callbackListener.onHekmatRegisterButton(hekmatCardNumberEt.getText().toString(), hekmatCardPasswordEt.getText().toString());
    }


    public interface OnHekmatCardCallbackListener {
        void onHekmatCardLoginDialogSubmitButton(String cardNumber, String password);

        void onHekmatRegisterButton(String cardNumber, String password);
    }

}
