package ir.etkastores.app.fragments.searchFragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.etkastores.app.activities.CategoriesFilterActivity;
import ir.etkastores.app.adapters.recyclerViewAdapters.CategoryRecyclerAdapter;
import ir.etkastores.app.models.CategoryModel;
import ir.etkastores.app.models.OauthResponse;
import ir.etkastores.app.R;
import ir.etkastores.app.ui.views.MessageView;
import ir.etkastores.app.webServices.ApiProvider;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Sajad on 1/5/18.
 */

public class CategoriesFragment extends Fragment implements CategoryRecyclerAdapter.OnCategoryItemClickListener {

    private final static String CATEGORY_ID_KEY = "CATEGORY_ID_KEY";
    private final static String TITLE_KEY = "TITLE_KEY";

    public static CategoriesFragment newInstance(long categoryId, String title) {
        CategoriesFragment categoriesFragment = new CategoriesFragment();
        Bundle bundle = new Bundle();
        bundle.putLong(CATEGORY_ID_KEY, categoryId);
        bundle.putString(TITLE_KEY, title);
        categoriesFragment.setArguments(bundle);
        return categoriesFragment;
    }

    @BindView(R.id.recyclerView)
    RecyclerView categoryRecyclerView;

    @BindView(R.id.circularProgress)
    ProgressBar circularProgress;

    @BindView(R.id.linearProgress)
    ProgressBar linearProgress;

    @BindView(R.id.messageView)
    MessageView messageView;

    private View view;

    Call<OauthResponse<List<CategoryModel>>> request;

    private CategoryRecyclerAdapter adapter;
    private long categoryId;
    private String title = "";

    private OnCategoryItemClickListener callback;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            categoryId = getArguments().getLong(CATEGORY_ID_KEY, 0);
            title = getArguments().getString(TITLE_KEY);
        } catch (Exception err) {
            categoryId = 0;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_categories, container, false);
            ButterKnife.bind(this, view);
            initViews();
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (callback != null) callback.onUpdateTitle(title);
    }

    private void initViews() {
        adapter = new CategoryRecyclerAdapter(getActivity());
        adapter.setOnCategoryItemClickListener(this);
        categoryRecyclerView.setAdapter(adapter);
        if (categoryId == 0) {
            loadCategoryAtRootLevel();
        } else {
            loadCategory();
        }
    }

    private void loadCategory() {
        showLoading();
        request = ApiProvider.getInstance().getAuthorizedApi().getCategory(categoryId);
        request.enqueue(new Callback<OauthResponse<List<CategoryModel>>>() {
            @Override
            public void onResponse(Call<OauthResponse<List<CategoryModel>>> call, Response<OauthResponse<List<CategoryModel>>> response) {
                if (response.isSuccessful()) {
                    if (response.body().isSuccessful()) {
                        adapter.setData(response.body().getData());
                    } else {
                        showError(response.body().getMeta().getMessage());
                    }
                } else {
                    onFailure(null, null);
                }
                hideLoading();
            }

            @Override
            public void onFailure(Call<OauthResponse<List<CategoryModel>>> call, Throwable t) {
                hideLoading();
            }
        });
    }

    private void loadCategoryAtRootLevel() {
        showLoading();
        messageView.hide();
        request = ApiProvider.getInstance().getAuthorizedApi().getCategoryAtLevel(1);
        request.enqueue(new Callback<OauthResponse<List<CategoryModel>>>() {
            @Override
            public void onResponse(Call<OauthResponse<List<CategoryModel>>> call, Response<OauthResponse<List<CategoryModel>>> response) {
                if (response.isSuccessful()) {
                    if (response.body().isSuccessful()) {
                        adapter.setData(response.body().getData());
                    } else {
                        showError(response.body().getMeta().getMessage());
                    }
                } else {
                    onFailure(null, null);
                }
                hideLoading();
            }

            @Override
            public void onFailure(Call<OauthResponse<List<CategoryModel>>> call, Throwable t) {
                hideLoading();
                showError(null);
            }
        });
    }

    @Override
    public void onCategoryItemClick(CategoryModel model, int position) {
        if (callback != null) callback.onCategoryClicked(model);
    }

    private void showError(String message) {
        if (!isAdded()) return;
        String messageText = getResources().getString(R.string.errorInDataReceiving);
        if (!TextUtils.isEmpty(message)) {
            messageText = message;
        }
        messageView.show(R.drawable.ic_warning_orange_48dp, messageText, getResources().getString(R.string.retry), new MessageView.OnMessageViewButtonClick() {
            @Override
            public void onMessageViewButtonClick() {
                loadCategoryAtRootLevel();
            }
        });
    }

    private void showLoading() {
        circularProgress.setVisibility(View.VISIBLE);
        linearProgress.setVisibility(View.VISIBLE);
    }

    private void hideLoading() {
        circularProgress.setVisibility(View.GONE);
        linearProgress.setVisibility(View.GONE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public interface OnCategoryItemClickListener {
        void onCategoryClicked(CategoryModel categoryModel);

        void onUpdateTitle(String title);
    }

    public void setOnCategoryItemClickListener(OnCategoryItemClickListener callback) {
        this.callback = callback;
    }
}
