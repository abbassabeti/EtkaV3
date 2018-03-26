package ir.etkastores.app.ui.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.rd.PageIndicatorView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.etkastores.app.DummyProvider;
import ir.etkastores.app.R;
import ir.etkastores.app.ui.widgets.ViewPager16x8;

/**
 * Created by Sajad on 11/24/17.
 */

public class CategorySliderView extends LinearLayout {

    @BindView(R.id.pager)
    ViewPager16x8 pager;

    @BindView(R.id.categoryPageIndicatorView)
    PageIndicatorView indicatorView;

    private CategoryAdapter adapter;

    List<String> items;

    public CategorySliderView(Context context) {
        super(context);
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
//        inflate(getContext(),R.layout.view_category_slider,this);
        LayoutInflater.from(getContext()).inflate(R.layout.view_category_slider,this,true);
        ButterKnife.bind(this,this);
        indicatorView.setFocusable(false);
        pager.setFocusable(false);
        fillView();
    }

    private void fillView(){
        items = new ArrayList<>();
        items.add("یک");
        items.add("دو");
        items.add("سه");

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
            return view == (RelativeLayout) object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view  = inflater.inflate(R.layout.home_slider_slide,container,false);
            ImageView imageView = (ImageView) view.findViewById(R.id.image);
            imageView.setImageResource(DummyProvider.getRandomSlider());
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((RelativeLayout) object);
        }
    }

}
