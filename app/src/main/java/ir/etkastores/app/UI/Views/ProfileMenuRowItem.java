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

public class ProfileMenuRowItem extends RelativeLayout {

    @BindView(R.id.starIcon)
    AppCompatImageView starIcon;
    @BindView(R.id.icon)
    AppCompatImageView icon;
    @BindView(R.id.text)
    TextView text;
    @BindView(R.id.scoreText)
    TextView scoreText;
    @BindView(R.id.scoreHolder)
    View scoreHolder;

    public ProfileMenuRowItem(Context context) {
        super(context);
        init(null);
    }

    public ProfileMenuRowItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public ProfileMenuRowItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs){
        View.inflate(getContext(),R.layout.view_profile_menu_row_item_layout,this);
        ButterKnife.bind(this);

        if (attrs !=null){
            TypedArray a = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.ProfileMenuRowItem, 0, 0);
            setText(a.getString(R.styleable.ProfileMenuRowItem_rowText));
            setScoreText(a.getString(R.styleable.ProfileMenuRowItem_scoreText));
            setIcon(a.getResourceId(R.styleable.ProfileMenuRowItem_rowIcon,0));
            showScore(a.getBoolean(R.styleable.ProfileMenuRowItem_showScore,false));
            showScoreStar(a.getBoolean(R.styleable.ProfileMenuRowItem_showScoreStar,false));
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
        icon.setImageResource(iconResId);
    }

}
