package ir.etkastores.app.Fragments.SupportFragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import ir.etkastores.app.R;

/**
 * Created by Sajad on 10/17/17.
 */

public class RequestsFragment extends Fragment {

    public static RequestsFragment newInstance(){
        return new RequestsFragment();
    }

    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_requests,container,false);
        ButterKnife.bind(this,view);
        initViews();
        return view;
    }

    private void initViews(){

    }

}
