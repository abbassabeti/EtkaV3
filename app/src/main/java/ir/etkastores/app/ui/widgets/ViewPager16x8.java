package ir.etkastores.app.ui.widgets;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

/**
 * Created by Sajad on 11/24/17.
 */

public class ViewPager16x8 extends ViewPager {

    public ViewPager16x8(Context context) {
        super(context);
    }

    public ViewPager16x8(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int h = Math.round((getMeasuredWidth()/16)*8);
        setMeasuredDimension(getMeasuredWidth(),h);
    }
}
