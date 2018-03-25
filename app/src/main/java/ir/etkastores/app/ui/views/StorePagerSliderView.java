package ir.etkastores.app.ui.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.AppCompatImageView;
import android.text.TextUtils;
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
import ir.etkastores.app.R;
import ir.etkastores.app.ui.widgets.ViewPager16x8;
import ir.etkastores.app.utils.image.ImageLoader;

/**
 * Created by Sajad on 11/24/17.
 */

public class StorePagerSliderView extends LinearLayout {

    @BindView(R.id.pager)
    ViewPager16x8 pager;

    @BindView(R.id.categoryPageIndicatorView)
    PageIndicatorView indicatorView;

    private StoreSliderAdapter adapter;

    List<String> items;

    public StorePagerSliderView(Context context) {
        super(context);
        init();
    }

    public StorePagerSliderView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public StorePagerSliderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        init();
    }

    private void init(){
        LayoutInflater.from(getContext()).inflate(R.layout.view_store_page_slider,this, true);
        ButterKnife.bind(this,this);
        items = new ArrayList<>();
    }

    public void setSlides(List<String> slides){
        this.items = slides;
        fillView();
    }

    private void fillView(){
        Collections.reverse(items);
        adapter = new StoreSliderAdapter(getContext(),items);
        pager.setAdapter(adapter);
        pager.setOffscreenPageLimit(items.size());
        indicatorView.setViewPager(pager);
        indicatorView.setSelection(items.size()-1);
        pager.setCurrentItem(items.size()-1);
        if (items.size()<2) indicatorView.setVisibility(INVISIBLE);
    }

    private class StoreSliderAdapter extends PagerAdapter{

        private List<String> items;

        public StoreSliderAdapter(Context context,List<String> items) {
            this.items = items;
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == (AppCompatImageView) object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            AppCompatImageView imageView = new AppCompatImageView(getContext());
            imageView.setLayoutParams(new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            imageView.setImageResource(R.drawable.etka_logo_wide);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            if (!TextUtils.isEmpty(items.get(position)))ImageLoader.loadProductImage(getContext(),imageView,items.get(position));
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((AppCompatImageView) object);
        }
    }

}
