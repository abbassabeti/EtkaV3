package ir.etkastores.app.UI.Views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import butterknife.ButterKnife;

/**
 * Created by Sajad on 2/3/18.
 */

public class ProductImagesSliderView extends RelativeLayout {

    public ProductImagesSliderView(Context context) {
        super(context);
        init();
    }

    public ProductImagesSliderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ProductImagesSliderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        LayoutInflater.from(getContext()).inflate(0,this,true);
        ButterKnife.bind(this,this);

    }

}
