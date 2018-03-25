package ir.etkastores.app.ui.widgets;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

/**
 * Created by Sajad on 11/24/17.
 */

public class ViewPager4x3 extends ViewPager {

    public ViewPager4x3(Context context) {
        super(context);
    }

    public ViewPager4x3(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int h = Math.round((getMeasuredWidth()/4)*3);
        setMeasuredDimension(getMeasuredWidth(),h);
    }
}
