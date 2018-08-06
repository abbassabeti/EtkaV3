package ir.etkastores.app.ui.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.etkastores.app.R;

/**
 * Created by Sajad on 9/1/17.
 */

public class EtkaToolbar extends Toolbar {

    @BindView(R.id.toolbarTitle)
    TextView titleTv;

    @BindView(R.id.toolbarBackButton)
    AppCompatImageView backButton;

    @BindView(R.id.toolbarMoreButton)
    AppCompatImageView moreButton;

    @BindView(R.id.toolbarSettingButton)
    AppCompatImageView settingButton;

    @BindView(R.id.toolbarMenuButton)
    AppCompatImageView filterButton;

    @BindView(R.id.toolbarNewsButton)
    View newsButton;

    @BindView(R.id.toolbarScannerButton)
    View scannerButton;

    @BindView(R.id.scannerIcon)
    AppCompatImageView scannerIcon;

    private EtkaToolbarActionsListener callback;

    public EtkaToolbar(Context context) {
        super(context);
        init(null);
    }

    public EtkaToolbar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public EtkaToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        View.inflate(getContext(), R.layout.toolbar_layout, this);
        ButterKnife.bind(this);
        setPadding(0, 0, 0, 0);
        setContentInsetsAbsolute(0, 0);
        if (attrs != null) {
            TypedArray a = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.EtkaToolbar, 0, 0);
            setTitle(a.getString(R.styleable.EtkaToolbar_title));
            showBack(a.getBoolean(R.styleable.EtkaToolbar_showBack, true));
            showMoreButton(a.getBoolean(R.styleable.EtkaToolbar_showMoreButton, false));
            showSettingButton(a.getBoolean(R.styleable.EtkaToolbar_showSettingButton, false));
            showNewsButton(a.getBoolean(R.styleable.EtkaToolbar_showNewsButton, false));
            showFilter(a.getBoolean(R.styleable.EtkaToolbar_showFilterButton, false));
            showScannerButton(a.getBoolean(R.styleable.EtkaToolbar_showScannerButton, false));
            a.recycle();
        }
    }

    @OnClick(R.id.toolbarBackButton)
    void onBackClick() {
        if (callback != null) callback.onToolbarBackClick();
    }

    @OnClick(R.id.toolbarMoreButton)
    void onMoreButtonClick() {
        if (callback != null) callback.onActionClick(EtkaToolbarActionsListener.MORE_BUTTON);
    }

    @OnClick(R.id.toolbarSettingButton)
    void onSettingButtonClick() {
        if (callback != null) callback.onActionClick(EtkaToolbarActionsListener.SETTING_BUTTON);
    }

    @OnClick(R.id.toolbarNewsButton)
    void onNewsButtonClick() {
        if (callback != null) callback.onActionClick(EtkaToolbarActionsListener.NEWS_BUTTON);
    }

    @OnClick(R.id.toolbarMenuButton)
    void onMenuButtonClick() {
        if (callback != null) callback.onActionClick(EtkaToolbarActionsListener.MENU_BUTTON);
    }

    @OnClick(R.id.toolbarScannerButton)
    void onScannerButtonClick() {
        if (callback != null) callback.onActionClick(EtkaToolbarActionsListener.SCANNER_BUTTON);
    }

    public void showFilter(boolean state) {
        if (state) {
            filterButton.setVisibility(VISIBLE);
        } else {
            filterButton.setVisibility(GONE);
        }
    }

    public void showBack(boolean state) {
        if (state) {
            backButton.setVisibility(VISIBLE);
        } else {
            backButton.setVisibility(GONE);
        }
    }

    public void showMoreButton(boolean state) {
        if (state) {
            moreButton.setVisibility(VISIBLE);
        } else {
            moreButton.setVisibility(GONE);
        }
    }

    public void showSettingButton(boolean state) {
        if (state) {
            settingButton.setVisibility(VISIBLE);
        } else {
            settingButton.setVisibility(GONE);
        }
    }

    public void showNewsButton(boolean state) {
        if (state) {
            newsButton.setVisibility(VISIBLE);
        } else {
            newsButton.setVisibility(GONE);
        }
    }

    public void showScannerButton(boolean state) {
        if (state) {
            scannerButton.setVisibility(VISIBLE);
            scannerIcon.setColorFilter(ContextCompat.getColor(getContext(), R.color.white));
        } else {
            scannerButton.setVisibility(GONE);
        }
    }

    public void setTitle(String title) {
        titleTv.setText(title);
    }

    public void setTitle(int titleResId) {
        titleTv.setText(titleResId);
    }

    public void setActionListeners(EtkaToolbarActionsListener callback) {
        this.callback = callback;
    }

    public interface EtkaToolbarActionsListener {
        int MORE_BUTTON = 1;
        int SETTING_BUTTON = 2;
        int NEWS_BUTTON = 3;
        int MENU_BUTTON = 4;
        int SCANNER_BUTTON = 5;

        void onToolbarBackClick();

        void onActionClick(int actionCode);
    }

    public String getTitle() {
        return titleTv.getText().toString();
    }

}
