package ir.etkastores.app.UI.Widgets;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

/**
 * Created by garshasbi on 3/2/18.
 */

public class ViewPagerSquare extends ViewPager {

    public ViewPagerSquare(@NonNull Context context) {
        super(context);
    }

    public ViewPagerSquare(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(),getMeasuredWidth());
    }

}
