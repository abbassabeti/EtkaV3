package ir.etkastores.app.UI.Dialogs;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.AppCompatImageView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.etkastores.app.EtkaApp;
import ir.etkastores.app.R;

/**
 * Created by Sajad on 2/5/18.
 */

public class MessageDialog extends BaseDialog {

    private final static String ICON = "ICON";
    private final static String TITLE = "TITLE";
    private final static String MESSAGE = "MESSAGE";
    private final static String RIGHT_BUTTON = "RIGHT_BUTTON";
    private final static String LEFT_BUTTON = "LEFT_BUTTON";

    @BindView(R.id.dialogIcon)
    AppCompatImageView icon;

    @BindView(R.id.dialogTitle)
    TextView title;

    @BindView(R.id.dialogMessage)
    TextView message;

    @BindView(R.id.rightButton)
    Button rightButton;

    @BindView(R.id.leftButton)
    Button leftButton;

    MessageDialogCallbacks callbacks;

    public static MessageDialog loginError() {
        return warningRetry(EtkaApp.getInstnace().getResources().getString(R.string.errorInLogin),
                EtkaApp.getInstnace().getResources().getString(R.string.anErrorHappendInServerConnection));
    }

    public static MessageDialog registerError(String messageText) {
        return warningRetry(EtkaApp.getInstnace().getResources().getString(R.string.errorInRegister), messageText);
    }

    public static MessageDialog networkError() {
        return null;
    }

    public static MessageDialog warningRetry(String title, String message) {
        return newInstance(R.drawable.ic_warning_orange_48dp,
                title,
                message,
                EtkaApp.getInstnace().getResources().getString(R.string.retry),
                EtkaApp.getInstnace().getResources().getString(R.string.cancel));
    }

    public static MessageDialog newInstance(int icon, String title, String message, String rightButton, String leftButton) {
        MessageDialog messageDialog = new MessageDialog();
        Bundle bundle = new Bundle();
        bundle.putInt(ICON, icon);
        bundle.putString(TITLE, title);
        bundle.putString(MESSAGE, message);
        bundle.putString(RIGHT_BUTTON, rightButton);
        bundle.putString(LEFT_BUTTON, leftButton);
        messageDialog.setArguments(bundle);
        return messageDialog;
    }

    public static MessageDialog newInstance(int icon, int title, int message, int rightButton, int leftButton) {
        return newInstance(icon,
                EtkaApp.getInstnace().getResources().getString(title),
                EtkaApp.getInstnace().getResources().getString(message),
                EtkaApp.getInstnace().getResources().getString(rightButton),
                EtkaApp.getInstnace().getResources().getString(leftButton));
    }

    private int iconId;
    private String titleText, messageText, rightButtonText, leftButtonText;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iconId = getArguments().getInt(ICON, 0);
        titleText = getArguments().getString(TITLE, null);
        messageText = getArguments().getString(MESSAGE, null);
        rightButtonText = getArguments().getString(RIGHT_BUTTON, null);
        leftButtonText = getArguments().getString(LEFT_BUTTON, null);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_message, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
    }

    private void initViews() {
        if (iconId != 0) {
            icon.setVisibility(View.VISIBLE);
            icon.setImageResource(iconId);
        }
        if (!TextUtils.isEmpty(titleText)) title.setText(titleText);
        if (!TextUtils.isEmpty(messageText)) message.setText(messageText);
        if (!TextUtils.isEmpty(rightButtonText)) {
            rightButton.setVisibility(View.VISIBLE);
            rightButton.setText(rightButtonText);
        }
        if (!TextUtils.isEmpty(leftButtonText)) {
            leftButton.setVisibility(View.VISIBLE);
            leftButton.setText(leftButtonText);
        }

        getDialog().setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (callbacks != null) callbacks.onDialogMessageDismiss();
            }
        });

    }

    @OnClick(R.id.rightButton)
    public void onRightButtonClick() {
        if (callbacks != null)
            callbacks.onDialogMessageButtonsClick(MessageDialogCallbacks.RIGHT_BUTTON);
    }

    @OnClick(R.id.leftButton)
    public void onRightLeftClick() {
        if (callbacks != null)
            callbacks.onDialogMessageButtonsClick(MessageDialogCallbacks.LEFT_BUTTON);
    }

    public void show(FragmentManager fragmentManager, boolean isCancelable, MessageDialogCallbacks callbacks) {
        setCancelable(isCancelable);
        this.callbacks = callbacks;
        show(fragmentManager, "");
    }

    public interface MessageDialogCallbacks {
        int LEFT_BUTTON = 1;
        int RIGHT_BUTTON = 2;

        void onDialogMessageButtonsClick(int button);

        void onDialogMessageDismiss();
    }

}