package ir.etkastores.app.UI.Widgets;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

/**
 * Created by Sajad on 11/24/17.
 */

public class ImageView16X9 extends AppCompatImageView {

    public ImageView16X9(Context context) {
        super(context);
    }

    public ImageView16X9(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ImageView16X9(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int calculatedHeight = Math.round((getMeasuredWidth()/16)*9);
        super.onMeasure(widthMeasureSpec, calculatedHeight);
        setMeasuredDimension(getMeasuredWidth(),calculatedHeight);
    }

}
