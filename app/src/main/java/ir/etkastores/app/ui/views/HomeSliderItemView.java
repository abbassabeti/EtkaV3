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
import ir.etkastores.app.activities.CategoriesFilterActivity;
import ir.etkastores.app.models.home.BannerModel;
import ir.etkastores.app.models.search.SearchProductRequestModel;
import ir.etkastores.app.ui.widgets.ViewPager16x9;
import ir.etkastores.app.utils.AdjustHelper;
import ir.etkastores.app.utils.IntentHelper;
import ir.etkastores.app.utils.image.ImageLoader;

/**
 * Created by Sajad on 11/24/17.
 */

public class HomeSliderItemView extends LinearLayout {

    @BindView(R.id.pager)
    ViewPager16x9 pager;

    @BindView(R.id.pageIndicatorView)
    PageIndicatorView indicatorView;

    private CategoryAdapter adapter;

    private List<BannerModel> items;

    public HomeSliderItemView(Context context, List<BannerModel> items) {
        super(context);
        this.items = items;
    }

    public HomeSliderItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public HomeSliderItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_category_slider, this, true);
        ButterKnife.bind(this, this);
        pager.setFocusable(false);
        fillView();
    }

    private void fillView() {
        Collections.reverse(items);
        adapter = new CategoryAdapter(getContext());
        pager.setAdapter(adapter);
        pager.setOffscreenPageLimit(items.size());
        pager.setCurrentItem(items.size() - 1);
        if (adapter.getCount() > 0) timer.start();
        indicatorView.setViewPager(pager);
        if (adapter.getCount() < 2) indicatorView.setVisibility(GONE);
    }

    private class CategoryAdapter extends PagerAdapter {

        private LayoutInflater inflater;

        public CategoryAdapter(Context context) {
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
            ImageLoader.loadImage(getContext(), imageView, items.get(position).getImageUrl());
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    AdjustHelper.sendAdjustEvent(AdjustHelper.ClickBannerHome);
                    BannerModel model = items.get(position);
                    if (!TextUtils.isEmpty(model.getExternalUrl())) {
                        IntentHelper.showWeb(getContext(), model.getExternalUrl());
                    }
                    SearchProductRequestModel searchProductRequestModel = model.getSearchProductRequest();
                    if (searchProductRequestModel != null) {
                        CategoriesFilterActivity.show(getContext(), searchProductRequestModel);
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

    CountDownTimer timer = new CountDownTimer(4000, 4000) {
        @Override
        public void onTick(long millisUntilFinished) {

        }

        @Override
        public void onFinish() {
            try {
                int currentItem = pager.getCurrentItem();
                if (currentItem > 0) {
                    currentItem--;
                } else {
                    currentItem = adapter.getCount() - 1;
                }
                pager.setCurrentItem(currentItem);
                timer.start();
            } catch (Exception err) {
                err.printStackTrace();
            }
        }
    };

}
