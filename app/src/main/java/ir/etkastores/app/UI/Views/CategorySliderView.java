package ir.etkastores.app.UI.Views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rd.PageIndicatorView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.etkastores.app.R;
import ir.etkastores.app.UI.Widgets.HomeHeaderSliderPager;

/**
 * Created by Sajad on 11/24/17.
 */

public class CategorySliderView extends LinearLayout {

    @BindView(R.id.pager)
    HomeHeaderSliderPager pager;

    @BindView(R.id.categoryPageIndicatorView)
    PageIndicatorView indicatorView;

    private CategoryAdapter adapter;

    public CategorySliderView(Context context) {
        super(context);
        init();
    }

    public CategorySliderView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CategorySliderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        init();
    }

    private void init(){
        View.inflate(getContext(), R.layout.view_category_slider,this);
        ButterKnife.bind(this);

        List<String> items = new ArrayList<>();
        items.add("یک");
        items.add("دو");
        items.add("سه");
        items.add("چهار");

        Collections.reverse(items);
        adapter = new CategoryAdapter(getContext(),items);
        pager.setAdapter(adapter);
        pager.setOffscreenPageLimit(items.size());
        indicatorView.setViewPager(pager);
        indicatorView.setSelection(items.size()-1);
        pager.setCurrentItem(items.size()-1);
    }

    private class CategoryAdapter extends PagerAdapter{

        private List<String> items;
        private LayoutInflater inflater;

        public CategoryAdapter(Context context,List<String> items) {
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

}
