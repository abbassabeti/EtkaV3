package ir.etkastores.app.Adapters.ViewPagerAdapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ir.etkastores.app.R;

/**
 * Created by Sajad on 11/24/17.
 */

public class HomeCategorySliderPagerAdapter extends PagerAdapter{

    private List<String> items;
    private Context context;
    private LayoutInflater inflater;

    public HomeCategorySliderPagerAdapter(Context context,List<String> items) {
        this.context = context;
        this.items = items;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view  = inflater.inflate(R.layout.home_slider_slide,container,false);
        ImageView imageView = (ImageView) view.findViewById(R.id.image);
        TextView testLable = (TextView) view.findViewById(R.id.testLable);
        testLable.setText(items.get(position));
        imageView.setImageResource(R.drawable.etka_logo_wide);
        container.addView(view,0);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

}
