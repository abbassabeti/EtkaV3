package ir.etkastores.app.activities;

import android.os.Bundle;

import ir.etkastores.app.EtkaApp;
import ir.etkastores.app.R;

public class InStoreModeActivity extends BaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_store_mode);
    }

    @Override
    protected void onResume() {
        super.onResume();
        EtkaApp.getInstance().screenView("In Store Mode Activity");
    }

}
