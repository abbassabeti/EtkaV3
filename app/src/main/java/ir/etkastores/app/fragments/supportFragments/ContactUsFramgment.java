package ir.etkastores.app.fragments.supportFragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.etkastores.app.R;

/**
 * Created by Sajad on 10/17/17.
 */

public class ContactUsFramgment extends Fragment {

    public static ContactUsFramgment newInstance() {
        return new ContactUsFramgment();
    }

    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_contact_us,container,false);
        ButterKnife.bind(this,view);
        initViews();
        return view;
    }

    private void initViews(){

    }

    @OnClick(R.id.directCallToPRButton)
    public void onDirectCallToPRButtonClick(){

    }

    @OnClick(R.id.sendMailButton)
    public void sendMailButtonClick(){

    }

    @OnClick(R.id.webSiteButton)
    public void webSiteButtonClick(){

    }
}
