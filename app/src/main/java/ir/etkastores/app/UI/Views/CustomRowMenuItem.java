package ir.etkastores.app.UI.Views;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.etkastores.app.R;

/**
 * Created by Sajad on 10/6/17.
 */

public class CustomRowMenuItem extends RelativeLayout {

    @BindView(R.id.starIcon)
    AppCompatImageView starIcon;
    @BindView(R.id.icon)
    AppCompatImageView icon;
    @BindView(R.id.leftIcon)
    AppCompatImageView leftIcon;
    @BindView(R.id.text)
    TextView text;
    @BindView(R.id.scoreText)
    TextView scoreText;
    @BindView(R.id.leftText)
    TextView leftText;
    @BindView(R.id.scoreHolder)
    View scoreHolder;

    public CustomRowMenuItem(Context context) {
        super(context);
        init(null);
    }

    public CustomRowMenuItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public CustomRowMenuItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs){
        View.inflate(getContext(),R.layout.view_profile_menu_row_item_layout,this);
        ButterKnife.bind(this);

        if (attrs !=null){
            TypedArray a = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.CustomRowMenuItem, 0, 0);
            setText(a.getString(R.styleable.CustomRowMenuItem_rowText));
            setScoreText(a.getString(R.styleable.CustomRowMenuItem_scoreText));
            showIcon(a.getBoolean(R.styleable.CustomRowMenuItem_showIcon,false));
            setIcon(a.getResourceId(R.styleable.CustomRowMenuItem_rowIcon,0));
            showScore(a.getBoolean(R.styleable.CustomRowMenuItem_showScore,false));
            showScoreStar(a.getBoolean(R.styleable.CustomRowMenuItem_showScoreStar,false));
            hideLeftIcon(a.getBoolean(R.styleable.CustomRowMenuItem_hideLeftIcon,false));
            setLeftText(a.getString(R.styleable.CustomRowMenuItem_leftText));
            setLeftIcon(a.getResourceId(R.styleable.CustomRowMenuItem_leftIcon,0));
            a.recycle();
        }
    }

    public void setText(String textValue){
        if (textValue == null) return;
        this.text.setText(textValue);
    }

    public void setScoreText(String scoreValue){
        if (scoreValue == null) return;
        scoreText.setText(scoreValue);
    }

    public void showScore(boolean state){
        if (state){
            scoreHolder.setVisibility(VISIBLE);
        }else{
            scoreHolder.setVisibility(GONE);
        }
    }

    public void showScoreStar(boolean state){
        if (state){
            starIcon.setVisibility(VISIBLE);
        }else{
            starIcon.setVisibility(GONE);
        }
    }

    public void setIcon(int iconResId){
        if (iconResId == 0 ) return;
        showIcon(true);
        icon.setImageResource(iconResId);
    }

    public void showIcon(boolean state){
        if (state){
            icon.setVisibility(VISIBLE);
        }else{
            icon.setVisibility(GONE);
        }
    }

    public void setLeftText(String text){
        if (text == null) return;
        leftIcon.setVisibility(GONE);
        scoreHolder.setVisibility(GONE);
        leftText.setText(text);
        leftText.setVisibility(VISIBLE);
        icon.setVisibility(GONE);
    }

    public void hideLeftIcon(boolean state){
        if (state){
            leftIcon.setVisibility(GONE);
        }else{
            leftIcon.setVisibility(VISIBLE);
        }
    }

    public void setLeftIcon(int icon){
        if (icon>0) leftIcon.setImageResource(icon);
    }

}
