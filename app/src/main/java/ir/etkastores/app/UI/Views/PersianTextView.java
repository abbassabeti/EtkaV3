package ir.etkastores.app.UI.Views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Sajad on 9/1/17.
 */

public class PersianTextView extends android.support.v7.widget.AppCompatTextView {

    public PersianTextView(Context context) {
        super(context);
        init();
    }

    public PersianTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PersianTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        if (!isInEditMode()){

        }
    }
}
