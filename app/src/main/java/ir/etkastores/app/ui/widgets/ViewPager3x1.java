package ir.etkastores.app.ui.widgets;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

/**
 * Created by garshasbi on 5/4/18.
 */

public class ViewPager3x1 extends ViewPager {

    public ViewPager3x1(Context context) {
        super(context);
    }

    public ViewPager3x1(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int h = Math.round((getMeasuredWidth() / 3) * 1);
        setMeasuredDimension(getMeasuredWidth(), h);
    }

}
