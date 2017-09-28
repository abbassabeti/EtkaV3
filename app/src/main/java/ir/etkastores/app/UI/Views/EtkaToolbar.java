package ir.etkastores.app.UI.Views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.etkastores.app.Fragments.HomeFragment;
import ir.etkastores.app.Fragments.MapFragment;
import ir.etkastores.app.Fragments.ProfileFragment;
import ir.etkastores.app.Fragments.SearchFragment;
import ir.etkastores.app.R;

/**
 * Created by Sajad on 9/1/17.
 */

public class EtkaToolbar extends Toolbar {

    @BindView(R.id.toolbarTitle)
    PersianTextView titleTv;

    @BindView(R.id.toolbarBackButton)
    AppCompatImageView backButton;

    private EtkaToolbarActionsListener callback;

    public EtkaToolbar(Context context) {
        super(context);
        init();
    }

    public EtkaToolbar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EtkaToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View.inflate(getContext(), R.layout.toolbar_layout, this);
        ButterKnife.bind(this);
        setPadding(0, 0, 0, 0);
        setContentInsetsAbsolute(0, 0);
    }

    @OnClick(R.id.toolbarBackButton)
    void onBackClick(){
        if (callback != null) callback.onToolbarBackClick();
    }

    public void hideBack() {
        backButton.setVisibility(GONE);
    }

    public void showBack(){
        backButton.setVisibility(GONE);
    }

    public void setTitle(String title) {
        titleTv.setText(title);
    }

    public void setTitle(int titleResId) {
        titleTv.setText(titleResId);
    }

    public void setActionListeners(EtkaToolbarActionsListener callback){
        this.callback = callback;
    }

    public interface EtkaToolbarActionsListener{
        void onToolbarBackClick();
        void onActionClick(int actionCode);
    }

}
