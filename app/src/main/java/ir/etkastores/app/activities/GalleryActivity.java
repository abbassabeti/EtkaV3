package ir.etkastores.app.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.etkastores.app.EtkaApp;
import ir.etkastores.app.R;
import ir.etkastores.app.adapters.recyclerViewAdapters.GalleryRecyclerHorizontalListAdapter;
import ir.etkastores.app.models.GalleryItemsModel;
import ir.etkastores.app.ui.views.EtkaToolbar;

public class GalleryActivity extends BaseActivity implements EtkaToolbar.EtkaToolbarActionsListener, GalleryRecyclerHorizontalListAdapter.OnImageSelectListener {

    private static final String MODEL = "MODEL";

    public static void show(Context context, GalleryItemsModel galleryItemsModel) {
        Intent intent = new Intent(context, GalleryActivity.class);
        intent.putExtra(MODEL, galleryItemsModel.toJson());
        context.startActivity(intent);
    }

    @BindView(R.id.toolbar)
    EtkaToolbar toolbar;

    @BindView(R.id.pager)
    ViewPager pager;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private GalleryItemsModel galleryItemsModel;

    private GalleryRecyclerHorizontalListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        ButterKnife.bind(this);
        galleryItemsModel = GalleryItemsModel.fromJson(getIntent().getStringExtra(MODEL));
        initViews();
    }

    private void initViews(){
        toolbar.setActionListeners(this);
        toolbar.setTitle(galleryItemsModel.getTitle());
        adapter = new GalleryRecyclerHorizontalListAdapter(this,galleryItemsModel.getImages());
        adapter.setOnImageSelectListener(this);
        recyclerView.setAdapter(adapter);

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

    }

}
