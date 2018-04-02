package ir.etkastores.app.fragments.supportFragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.etkastores.app.EtkaApp;
import ir.etkastores.app.R;
import ir.etkastores.app.data.ContactUsManager;
import ir.etkastores.app.utils.IntentHelper;

/**
 * Created by Sajad on 10/17/17.
 */

public class ContactUsFragment extends Fragment {

    public static ContactUsFragment newInstance() {
        return new ContactUsFragment();
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

    @Override
    public void onResume() {
        super.onResume();
        EtkaApp.getInstance().screenView("Contact Us Fragment");
    }

    private void initViews(){

    }

    @OnClick(R.id.directCallToPRButton)
    public void onDirectCallToPRButtonClick(){
        IntentHelper.showDialer(getActivity(), ContactUsManager.getInstance().getPhone());
    }

    @OnClick(R.id.sendMailButton)
    public void sendMailButtonClick(){
        IntentHelper.sendEmail(getActivity(), ContactUsManager.getInstance().getEmail(),"","");
    }

//    @OnClick(R.id.webSiteButton)
//    public void webSiteButtonClick(){
//
//    }

}
