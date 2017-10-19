package ir.etkastores.app.UI.Views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.Field;

import ir.etkastores.app.Utils.FontUtils;

/**
 * Created by Sajad on 10/19/17.
 */

public class RTLTabLayout extends TabLayout {

    private Typeface mTypeface;

    public RTLTabLayout(Context context) {
        super(context);
        initTypeface();
        RtlGravity();
    }

    public RTLTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initTypeface();
        RtlGravity();
    }

    public RTLTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initTypeface();
        RtlGravity();
    }

    @Override
    public void addTab(@NonNull Tab tab) {
        super.addTab(tab,0);
//        super.addTab(tab);
        updateTypeface(tab);
    }

    @Override
    public void addTab(@NonNull Tab tab, int position) {
        super.addTab(tab, position);
        updateTypeface(tab);
    }

    @Override
    public void addTab(@NonNull Tab tab, int position, boolean setSelected) {
        super.addTab(tab, position, setSelected);
        updateTypeface(tab);
    }

    @Override
    public void addTab(@NonNull Tab tab, boolean setSelected) {
        super.addTab(tab, setSelected);
        updateTypeface(tab);
    }

    private void initTypeface() {
        if (!isInEditMode()) {
            mTypeface = FontUtils.getCommonTypeFace();
        }
    }

    private void updateTypeface(Tab tab) {
        if (mTypeface == null) return;

        ViewGroup mainView = (ViewGroup) getChildAt(0);
        ViewGroup tabView = (ViewGroup) mainView.getChildAt(tab.getPosition());

        int tabChildCount = tabView.getChildCount();
        for (int i = 0; i < tabChildCount; i++) {
            View tabViewChild = tabView.getChildAt(i);
            if (tabViewChild instanceof TextView) {
                ((TextView) tabViewChild).setTypeface(mTypeface, Typeface.NORMAL);
            }
        }
    }

    @SuppressLint("RtlHardcoded")
    private void RtlGravity() {
        Field f;
        try {
            f = android.support.design.widget.TabLayout.class.getDeclaredField("mTabStrip");
            f.setAccessible(true);
            try {
                LinearLayout tabStrip = (LinearLayout) f.get(this);
                tabStrip.setGravity(Gravity.RIGHT);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setupWithViewPager(ViewPager viewPager) {
        super.setupWithViewPager(viewPager);

        int adapterCount = viewPager == null ?
                0 : viewPager.getAdapter().getCount();

        if (adapterCount > 0) {
            int position = adapterCount - 1;
            if (position >= 0) {
                viewPager.setCurrentItem(position);
            }
        }
    }

}
