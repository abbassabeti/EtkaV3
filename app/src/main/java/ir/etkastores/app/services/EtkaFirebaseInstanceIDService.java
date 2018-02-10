package ir.etkastores.app.services;

import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by Sajad on 2/11/18.
 */

public class EtkaFirebaseInstanceIDService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();

    }
}
