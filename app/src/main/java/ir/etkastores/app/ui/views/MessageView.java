package ir.etkastores.app.ui.views;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.etkastores.app.R;

/**
 * Created by Sajad on 2/7/18.
 */

public class MessageView extends RelativeLayout {

    @BindView(R.id.icon)
    AppCompatImageView icon;

    @BindView(R.id.message)
    TextView message;

    @BindView(R.id.button)
    Button button;

    private int iconId;
    private String messageValue;
    private String buttonValue;

    public MessageView(Context context) {
        super(context);
        init(null);
    }

    public MessageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public MessageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        LayoutInflater.from(getContext()).inflate(R.layout.view_message, this, true);
        ButterKnife.bind(this, this);
        setVisibility(GONE);
    }

    public void show(int iconId, int messageId, int buttonId, final OnMessageViewButtonClick callback){
        setVisibility(VISIBLE);
        icon.setImageResource(iconId);
        message.setText(messageId);
        if (buttonId == 0){
            button.setVisibility(GONE);
        }else{
            button.setText(buttonId);
            button.setVisibility(VISIBLE);
            button.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (callback != null) callback.onMessageViewButtonClick();
                }
            });
        }
    }

    public void show(int iconId, String messagetext, String buttonTitle, final OnMessageViewButtonClick callback){
        setVisibility(VISIBLE);
        icon.setImageResource(iconId);
        message.setText(messagetext);
        if (TextUtils.isEmpty(buttonTitle)){
            button.setVisibility(GONE);
        }else{
            button.setText(buttonTitle);
            button.setVisibility(VISIBLE);
            button.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (callback != null) callback.onMessageViewButtonClick();
                }
            });
        }
    }

    public interface OnMessageViewButtonClick{
        void onMessageViewButtonClick();
    }

    public void hide(){
        setVisibility(GONE);
    }

}
