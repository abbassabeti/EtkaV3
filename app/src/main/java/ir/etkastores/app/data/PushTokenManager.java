package ir.etkastores.app.data;

public class PushTokenManager {

    private static PushTokenManager instance;

    public static PushTokenManager getInstance() {
        if (instance == null) instance = new PushTokenManager();
        return instance;
    }

    private PushTokenManager(){

    }

    public void updateToken(String token){

    }

    public void syncToken(){

    }

    public void inTokenSynced(){

    }


    private void sendRequest(){

    }

}
