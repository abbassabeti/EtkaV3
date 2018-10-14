package ir.etkastores.app.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.michaelrocks.paranoid.Obfuscate;
import ir.etkastores.app.EtkaApp;
import ir.etkastores.app.R;
import ir.etkastores.app.adapters.recyclerViewAdapters.GalleryRecyclerHorizontalListAdapter;
import ir.etkastores.app.adapters.viewPagerAdapters.GalleryPagerAdapter;
import ir.etkastores.app.models.GalleryItemsModel;
import ir.etkastores.app.models.ProductModel;
import ir.etkastores.app.ui.views.EtkaToolbar;
import ir.etkastores.app.ui.widgets.ZoomableViewPager;
import ir.etkastores.app.utils.ZoomOutSlidePagerTransformer;

@Obfuscate
public class GalleryActivity extends BaseActivity implements EtkaToolbar.EtkaToolbarActionsListener,
        GalleryRecyclerHorizontalListAdapter.OnImageSelectListener,
        ViewPager.OnPageChangeListener {

    private static final String MODEL = "MODEL";

    public static void show(Context context, ProductModel productModel, int position) {
        if (productModel == null || productModel.getImageUrl() == null || productModel.getImageUrl().size() == 0) {
            return;
        }
        GalleryItemsModel galleryItemsModel = new GalleryItemsModel(productModel.getTitle(), productModel.getImageUrl(), position);
        show(context, galleryItemsModel);
    }

    public static void show(Context context, GalleryItemsModel galleryItemsModel) {
        Intent intent = new Intent(context, GalleryActivity.class);
        intent.putExtra(MODEL, galleryItemsModel.toJson());
        context.startActivity(intent);
    }

    @BindView(R.id.toolbar)
    EtkaToolbar toolbar;

    @BindView(R.id.pager)
    ZoomableViewPager pager;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.imagesThumbHolder)
    FrameLayout imagesThumbHolder;

    private GalleryItemsModel galleryItemsModel;

    private GalleryRecyclerHorizontalListAdapter thumbAdapter;
    private GalleryPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        ButterKnife.bind(this);
        galleryItemsModel = GalleryItemsModel.fromJson(getIntent().getStringExtra(MODEL));
        initViews();
    }

    private void initViews() {
        toolbar.setActionListeners(this);
        toolbar.setTitle(galleryItemsModel.getTitle());
        thumbAdapter = new GalleryRecyclerHorizontalListAdapter(this, galleryItemsModel.getImages());
        thumbAdapter.setOnImageSelectListener(this);
        recyclerView.setAdapter(thumbAdapter);
        pagerAdapter = new GalleryPagerAdapter(this, galleryItemsModel.getImages());
        pager.setAdapter(pagerAdapter);
        pager.setPageTransformer(true, new ZoomOutSlidePagerTransformer());
        pager.addOnPageChangeListener(this);
        pager.setCurrentItem(galleryItemsModel.getImages().size() - 1 - galleryItemsModel.getPosition(), false);
        if (galleryItemsModel.getImages().size() == 1){
            imagesThumbHolder.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        EtkaApp.getInstance().screenView("Gallery Activity");
    }

    @Override
    public void onToolbarBackClick() {
        onBackPressed();
    }

    @Override
    public void onActionClick(int actionCode) {

    }

    @Override
    public void onImageSelect(String img, int index) {
        pager.setCurrentItem(index);
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        recyclerView.scrollToPosition(i);
        thumbAdapter.setSelectedItem(i);
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

}
