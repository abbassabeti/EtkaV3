package ir.etkastores.app.ui.dialogs;

import android.os.Bundle;
import android.support.annotation.NonNull;
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
import ir.etkastores.app.ui.Toaster;

public class TicketResponseDialog extends BaseDialog {

    @BindView(R.id.message)
    EditText messageEt;

    private TicketResponseCallback callback;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_ticket_reply, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    public void show(FragmentManager fm, TicketResponseCallback callback) {
        this.callback = callback;
        show(fm, "TicketResponseDialog");
    }

    public interface TicketResponseCallback {
        void onSendButtonClick(String message);
    }

    @OnClick(R.id.sendButton)
    public void onSendMessageButtonClick() {
        if (messageEt.getText().toString().isEmpty()) {
            Toaster.show(getContext(), R.string.pleaseWriteMessage);
        } else {
            if (callback != null) callback.onSendButtonClick(messageEt.getText().toString());
        }
    }

}
