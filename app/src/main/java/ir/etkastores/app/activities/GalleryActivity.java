package ir.etkastores.app.activities;

import android.os.Bundle;

import ir.etkastores.app.EtkaApp;
import ir.etkastores.app.R;

public class GalleryActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
    }

    @Override
    protected void onResume() {
        super.onResume();
        EtkaApp.getInstance().screenView("Gallery Activity");
    }
}
