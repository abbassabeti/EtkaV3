package ir.etkastores.app.Fragments.SearchFragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.etkastores.app.R;
import ir.etkastores.app.UI.Views.EtkaToolbar;
import ir.etkastores.app.Utils.ActivityUtils;

/**
 * Created by Sajad on 9/1/17.
 */

public class SearchTabFragment extends Fragment {

    private View view;

    @BindView(R.id.toolbar)
    EtkaToolbar toolbar;

    @BindView(R.id.searchEditText)
    EditText searchInput;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null){
            view = inflater.inflate(R.layout.fragment_search_tab,container,false);
            ButterKnife.bind(this,view);
            initViews();
        }
        return view;
    }

    private void initViews(){

        showCategories();
        searchInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
//                if (b) showAdvancedSearch();
            }
        });

    }

    private void showCategories(){
        ActivityUtils.addFragment(getActivity(),R.id.searchContentFrame,CategoriesFragment.newInstance(0),"",false);
    }

    private void showAdvancedSearch(){
        ActivityUtils.replaceFragment(getActivity(),R.id.searchContentFrame,SearchAdvancedFragment.newInstance(),"",false);
    }

}
