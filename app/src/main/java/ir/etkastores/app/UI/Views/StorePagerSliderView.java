package ir.etkastores.app.UI.Views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rd.PageIndicatorView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.etkastores.app.R;
import ir.etkastores.app.UI.Widgets.ViewPager16x9;

/**
 * Created by Sajad on 11/24/17.
 */

public class StorePagerSliderView extends LinearLayout {

    @BindView(R.id.pager)
    ViewPager16x9 pager;

    @BindView(R.id.categoryPageIndicatorView)
    PageIndicatorView indicatorView;

    private StoreSliderAdapter adapter;

    List<String> items;

    public StorePagerSliderView(Context context) {
        super(context);
    }

    public StorePagerSliderView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public StorePagerSliderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        init();
    }

    private void init(){
        inflate(getContext(), R.layout.view_store_page_slider,this);
        ButterKnife.bind(this);
        fillView();
    }

    private void fillView(){
        items = new ArrayList<>();

        items.add("");
        items.add("");
        items.add("");
        items.add("");
        items.add("");

        Collections.reverse(items);
        adapter = new StoreSliderAdapter(getContext(),items);
        pager.setAdapter(adapter);
        pager.setOffscreenPageLimit(items.size());
        indicatorView.setViewPager(pager);
        indicatorView.setSelection(items.size()-1);
        pager.setCurrentItem(items.size()-1);
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
            return view == (ImageView) object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(getContext());
            imageView.setLayoutParams(new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            imageView.setImageResource(R.drawable.etka_logo_wide);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((ImageView) object);
        }
    }

}
