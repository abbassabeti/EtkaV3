package ir.etkastores.app.UI.Widgets;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

/**
 * Created by Sajad on 11/24/17.
 */

public class HomeHeaderSliderPager extends ViewPager {

    public HomeHeaderSliderPager(Context context) {
        super(context);
    }

    public HomeHeaderSliderPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int h = Math.round((getMeasuredWidth()/16)*9);
        setMeasuredDimension(getMeasuredWidth(),h);
    }
}
