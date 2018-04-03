package ir.etkastores.app.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eftimoff.viewpagertransformers.RotateUpTransformer;
import com.rd.PageIndicatorView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.etkastores.app.EtkaApp;
import ir.etkastores.app.R;
import ir.etkastores.app.data.ProfileManager;
import ir.etkastores.app.utils.FontUtils;

public class WalkthroughActivity extends BaseActivity {

    public static void show(Context context) {
        Intent intent = new Intent(context, WalkthroughActivity.class);
        context.startActivity(intent);
    }

    @BindView(R.id.pager)
    ViewPager pager;

    @BindView(R.id.pageIndicatorView)
    PageIndicatorView indicatorView;

    @BindView(R.id.enterButton)
    TextView enterButton;

    private WalkthroughPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walkthrough);
        ButterKnife.bind(this);
        initViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        EtkaApp.getInstance().screenView("Walkthrough Activity");
    }

    private void initViews() {
        ProfileManager.setIsFirstRun(false);
        enterButton.setTypeface(FontUtils.getBoldTypeFace());
        List<PagerSliderModel> items = new ArrayList<>();
        items.add(new PagerSliderModel(0, "", ""));
        items.add(new PagerSliderModel(0, "", ""));
        items.add(new PagerSliderModel(0, "", ""));
        items.add(new PagerSliderModel(0, "", ""));
        items.add(new PagerSliderModel(0, "", ""));
        items.add(new PagerSliderModel(0, "", ""));
        items.add(new PagerSliderModel(0, "", ""));
        items.add(new PagerSliderModel(0, "", ""));
        Collections.reverse(items);
        adapter = new WalkthroughPagerAdapter(this, items);
        pager.setAdapter(adapter);
        pager.setPageTransformer(true, new RotateUpTransformer());
        indicatorView.setViewPager(pager);
        pager.setCurrentItem(adapter.getCount() - 1);
    }

    @OnClick(R.id.enterButton)
    public void onIKnownButtonClick(){
        MainActivity.show(this,null);
        finish();
    }

    private class WalkthroughPagerAdapter extends PagerAdapter {

        private List<PagerSliderModel> items;
        private LayoutInflater inflater;

        public WalkthroughPagerAdapter(Context context, List<PagerSliderModel> items) {
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
            View view = inflater.inflate(R.layout.walkthrough_slider_slide, container, false);
            ImageView imageView = (ImageView) view.findViewById(R.id.image);
            imageView.setImageResource(R.mipmap.ic_launcher);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((RelativeLayout) object);
        }
    }

    private class PagerSliderModel {

        int artWork;
        String title;
        String info;

        public PagerSliderModel(int artWork, String title, String info) {
            this.artWork = artWork;
            this.title = title;
            this.info = info;
        }

        public int getArtWork() {
            return artWork;
        }

        public String getTitle() {
            return title;
        }

        public String getInfo() {
            return info;
        }
    }

}
