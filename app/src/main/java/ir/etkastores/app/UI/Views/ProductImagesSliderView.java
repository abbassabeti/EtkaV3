package ir.etkastores.app.UI.Views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.AppCompatImageView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.rd.PageIndicatorView;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.etkastores.app.DummyProvider;
import ir.etkastores.app.R;
import ir.etkastores.app.UI.Widgets.ViewPagerSquare;
import ir.etkastores.app.Utils.Image.ImageLoader;

/**
 * Created by Sajad on 11/24/17.
 */

public class ProductImagesSliderView extends LinearLayout {

    @BindView(R.id.pager)
    ViewPagerSquare pager;

    @BindView(R.id.categoryPageIndicatorView)
    PageIndicatorView indicatorView;

    private ProductImagesAdapter adapter;

    List<String> items;

    public ProductImagesSliderView(Context context) {
        super(context);
        init();
    }

    public ProductImagesSliderView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ProductImagesSliderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        LayoutInflater.from(getContext()).inflate(R.layout.view_product_slider,this,true);
        ButterKnife.bind(this,this);
        indicatorView.setFocusable(false);
        pager.setFocusable(false);
    }

    public void setImages(List<String> images){
        if (images == null || images.size() == 0 || (images.size() > 0 && TextUtils.isEmpty(images.get(0)))) {
            setVisibility(GONE);
        }else{
            setVisibility(VISIBLE);
            this.items = images;
            fillView();
        }
    }

    private void fillView(){
        Collections.reverse(items);
        adapter = new ProductImagesAdapter(getContext(),items);
        pager.setAdapter(adapter);
        pager.setOffscreenPageLimit(items.size());
        indicatorView.setViewPager(pager);
        indicatorView.setSelection(items.size()-1);
        pager.setCurrentItem(items.size()-1);
    }

    private class ProductImagesAdapter extends PagerAdapter{

        private List<String> items;
        private LayoutInflater inflater;

        public ProductImagesAdapter(Context context,List<String> items) {
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
            AppCompatImageView imageView = (AppCompatImageView) view.findViewById(R.id.image);
            ImageLoader.load(getContext(),imageView,items.get(position));
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((RelativeLayout) object);
        }
    }

}
