package ir.etkastores.app.ui.views;

import android.content.Context;
import android.os.CountDownTimer;
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
import ir.etkastores.app.R;
import ir.etkastores.app.ui.widgets.ViewPagerSquare;
import ir.etkastores.app.utils.image.ImageLoader;

/**
 * Created by Sajad on 11/24/17.
 */

public class ProductImagesSliderView extends LinearLayout {

    @BindView(R.id.pager)
    ViewPagerSquare pager;

    @BindView(R.id.categoryPageIndicatorView)
    PageIndicatorView indicatorView;

    private ProductImagesAdapter adapter;

    private List<String> items;

    private OnProductImageClickListener onProductImageClickListener;

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

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_product_slider, this, true);
        ButterKnife.bind(this, this);
        indicatorView.setFocusable(false);
        pager.setFocusable(false);
    }

    public void setImages(List<String> images) {
        if (images == null || images.size() == 0 || (images.size() > 0 && TextUtils.isEmpty(images.get(0)))) {
            setVisibility(GONE);
        } else {
            setVisibility(VISIBLE);
            this.items = images;
            fillView();
        }
    }

    private void fillView() {
        Collections.reverse(items);
        adapter = new ProductImagesAdapter(getContext(), items);
        pager.setAdapter(adapter);
        pager.setOffscreenPageLimit(items.size());
        indicatorView.setViewPager(pager);
        indicatorView.setSelection(items.size() - 1);
        pager.setCurrentItem(items.size() - 1);
        if (adapter.getCount() < 2) indicatorView.setVisibility(INVISIBLE);
    }

    public void setOnProductImageClickListener(OnProductImageClickListener onProductImageClickListener) {
        this.onProductImageClickListener = onProductImageClickListener;
    }

    public interface OnProductImageClickListener {
        void onProductImageClick(int position, String img);
    }

    private class ProductImagesAdapter extends PagerAdapter {

        private List<String> items;
        private LayoutInflater inflater;

        public ProductImagesAdapter(Context context, List<String> items) {
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
        public Object instantiateItem(ViewGroup container, final int position) {
            View view = inflater.inflate(R.layout.home_slider_slide, container, false);
            AppCompatImageView imageView = (AppCompatImageView) view.findViewById(R.id.image);
            ImageLoader.loadProductImage(getContext(), imageView, items.get(position));
            imageView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        if (onProductImageClickListener != null)
                            onProductImageClickListener.onProductImageClick(position, items.get(position));
                    } catch (Exception err) {
                        err.printStackTrace();
                    }
                }
            });
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((RelativeLayout) object);
        }
    }

}
