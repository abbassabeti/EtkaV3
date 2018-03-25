package ir.etkastores.app.fragments.searchFragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import ir.etkastores.app.R;

/**
 * Created by Sajad on 1/5/18.
 */

public class SearchAdvancedFragment extends Fragment {

    public static SearchAdvancedFragment newInstance(){
        SearchAdvancedFragment searchFragment = new SearchAdvancedFragment();
        return searchFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_advanced,container,false);
        ButterKnife.bind(this,view);
        return view;
    }

}
