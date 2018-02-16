package ir.etkastores.app.UI.Views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import butterknife.ButterKnife;
import ir.etkastores.app.R;

/**
 * Created by Sajad on 2/17/18.
 */

public class SpecialCategoriesView extends LinearLayout {

    public SpecialCategoriesView(Context context) {
        super(context);
        init();
    }

    public SpecialCategoriesView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SpecialCategoriesView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        LayoutInflater.from(getContext()).inflate(R.layout.view_special_categories,this,true);
        ButterKnife.bind(this,this);
    }

}
