package ir.etkastores.app.ui.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.etkastores.app.R;

/**
 * Created by Sajad on 10/9/17.
 */

public class EtkaIconTextImageButton extends CardView {

    @BindView(R.id.buttonIcon)
    AppCompatImageView icon;

    @BindView(R.id.buttonTitle)
    TextView title;

    public EtkaIconTextImageButton(Context context) {
        super(context);
        init(null);
    }

    public EtkaIconTextImageButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public EtkaIconTextImageButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        View.inflate(getContext(), R.layout.view_text_icon_button, this);
        setBackgroundResource(R.color.transparent);
        setCardBackgroundColor(ContextCompat.getColor(getContext(), R.color.transparent));
        ButterKnife.bind(this);
        if (attrs != null) {
            TypedArray a = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.EtkaIconTextImageButton, 0, 0);
            setText(a.getString(R.styleable.EtkaIconTextImageButton_text));
            setIcon(a.getResourceId(R.styleable.EtkaIconTextImageButton_icon, 0));
            a.recycle();
        }
    }

    public void setIcon(int resIconId) {
        if (resIconId != 0) {
            icon.setImageResource(resIconId);
        }
    }

    public void setText(int resTextId) {
        if (resTextId != 0) {
            title.setText(resTextId);
        }
    }

    public void setIcon(Drawable drawable) {
        if (drawable != null) {
            icon.setImageDrawable(drawable);
        }
    }

    public void setText(String text) {
        if (text != null) {
            title.setText(text);
        }
    }

}
