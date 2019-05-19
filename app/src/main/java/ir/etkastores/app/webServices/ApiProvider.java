package ir.etkastores.app.webServices;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.michaelrocks.paranoid.Obfuscate;
import ir.etkastores.app.BuildConfig;
import ir.etkastores.app.data.ProfileManager;
import ir.etkastores.app.models.ErrorModel;
import ir.etkastores.app.models.OauthResponse;
import ir.etkastores.app.utils.DiskDataHelper;
import ir.etkastores.app.utils.EtkaPushNotificationConfig;
import ir.etkastores.app.utils.EventsManager;
import ir.etkastores.app.utils.IntercentorHelper;
import okhttp3.CertificatePinner;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by Sajad on 9/3/17.
 */

@Obfuscate
public class ApiProvider {

    private OkHttpClient.Builder httpClient;
    private Retrofit.Builder builder;

    public static AccessToken lastToken;

    private static ApiProvider instance;

    private boolean isInTokenRefreshing = false;

    synchronized public static ApiProvider getInstance() {
        if (instance == null) instance = new ApiProvider();
        return instance;
    }

    public <S> S createService(Class<S> serviceClass) {
        httpClient = new OkHttpClient.Builder();
        builder = new Retrofit.Builder()
                .baseUrl(ApiStatics.getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create());

        addTLSSocketFactory(httpClient);

        if (BuildConfig.DEBUG) {
            httpClient.addInterceptor(getBodyLogInterceptor());
            httpClient.addInterceptor(getHeadersLogInterceptor());
        }

        setTimeOuts(httpClient);
        if (getPinnedCertificate() != null) httpClient.certificatePinner(getPinnedCertificate());
        OkHttpClient client = httpClient.build();
        Retrofit retrofit = builder.client(client).build();
        return retrofit.create(serviceClass);
    }

    public <S> S createService(Class<S> serviceClass, AccessToken accessToken) {
        httpClient = new OkHttpClient.Builder();
        builder = new Retrofit.Builder()
                .baseUrl(ApiStatics.getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create());

        addTLSSocketFactory(httpClient);

        if (accessToken == null) {
            accessToken = new AccessToken();
        }

        if (accessToken != null) {
            lastToken = accessToken;
            final AccessToken token = accessToken;
            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();

                    Request.Builder requestBuilder = original.newBuilder()
                            .header("Accept", "application/json")
                            .header("Content-type", "application/json")
                            .header("Authorization",
                                    token.getTokenType() + " " + token.getAccessToken())
                            .method(original.method(), original.body());

                    Request request = requestBuilder.build();
                    Response proceed = chain.proceed(request);

                    OauthResponse<Object> response = null;
                    try {
                        response = new Gson().fromJson(IntercentorHelper.getResponseBody(proceed), new TypeToken<OauthResponse<Object>>() {

                        }.getType());
                    } catch (Exception err) {
                        err.printStackTrace();
                        EventsManager.sendEvent("dev", "INTERCEPTOR_PARS_FAILED", "" + err.getLocalizedMessage());
                    }
                    if (response != null && response.getMeta() != null && response.getMeta().getStatusCode() == 401 & !isInTokenRefreshing) {
                        synchronized (httpClient) {
                            if (ApiStatics.getLastToken() == null) {
                                EventsManager.sendEvent("dev", "INTERCEPTOR_PARSED_CODE_410", "SAVED_TOKEN_NULL!!");
                                Call<AccessToken> call = guestLogin();
                                retrofit2.Response<AccessToken> tokenResponse = call.execute();
                                if (tokenResponse.code() == 200) {
                                    ProfileManager.getInstance().clearProfile();
                                    AccessToken newToken = tokenResponse.body();
                                    lastToken = newToken;
                                    ApiStatics.saveToken(lastToken);
                                    request = request.newBuilder()
                                            .header("Authorization", newToken.getTokenType() + " " + newToken.getAccessToken())
                                            .build();

                                    proceed = chain.proceed(request);
                                }
                                isInTokenRefreshing = false;
                            } else {
                                isInTokenRefreshing = true;
                                String refreshToken = ApiStatics.getLastToken().getRefreshToken();
                                EtkaApi tokenClient = createService(EtkaApi.class);

                                Call<AccessToken> call = tokenClient.getToken(
                                        ApiStatics.GRAND_TYPE_REFRESH_TOKEN,
                                        ApiStatics.getClientId(),
                                        ApiStatics.getClientSecret(),
                                        refreshToken);

                                retrofit2.Response<AccessToken> tokenResponse = call.execute();
                                EventsManager.sendEvent("dev", "INTERCEPTOR_PARSED_CODE_410", "REFRESHED_TOKEN_CODE_" + tokenResponse.code());
                                ErrorModel errorModel = null;
                                try {
                                    errorModel = new Gson().fromJson(tokenResponse.errorBody().charStream(), ErrorModel.class);
                                } catch (Exception err) {
                                    err.printStackTrace();
                                }
                                if (tokenResponse.code() == 200) {
                                    AccessToken newToken = tokenResponse.body();
                                    lastToken = newToken;
                                    ApiStatics.saveToken(lastToken);
                                    request = request.newBuilder()
                                            .header("Accept", "application/json")
                                            .header("Content-type", "application/json")
                                            .header("Authorization", newToken.getTokenType() + " " + newToken.getAccessToken())
                                            .build();
                                    proceed = chain.proceed(request);
                                } else if (tokenResponse.code() == 400 &&
                                        errorModel != null &&
                                        !TextUtils.isEmpty(errorModel.getError())) {
                                    Call<AccessToken> callGust = guestLogin();
                                    retrofit2.Response<AccessToken> guestResponse = callGust.execute();
                                    if (guestResponse.code() == 200) {
                                        ProfileManager.getInstance().clearProfile();
                                        AccessToken guestToken = guestResponse.body();
                                        lastToken = guestToken;
                                        ApiStatics.saveToken(lastToken);
                                        request = request.newBuilder()
                                                .header("Authorization", guestToken.getTokenType() + " " + guestToken.getAccessToken())
                                                .build();

                                        proceed = chain.proceed(request);
                                    }
                                }
                                isInTokenRefreshing = false;
                            }
                        }
                    }
                    return proceed;
                }
            });

        }

        if (BuildConfig.DEBUG) {
            httpClient.addInterceptor(getBodyLogInterceptor());
            httpClient.addInterceptor(getHeadersLogInterceptor());
        }

        setTimeOuts(httpClient);
        if (getPinnedCertificate() != null) httpClient.certificatePinner(getPinnedCertificate());
        OkHttpClient client = httpClient.build();
        Retrofit retrofit = builder.client(client).build();
        return retrofit.create(serviceClass);
    }

    public EtkaApi getApi() {
        return createService(EtkaApi.class);
    }

    public EtkaApi getAuthorizedApi() {
        return createService(EtkaApi.class, DiskDataHelper.getLastToken());
    }

    private HttpLoggingInterceptor getBodyLogInterceptor() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        return logging;
    }

    private HttpLoggingInterceptor getHeadersLogInterceptor() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        return logging;
    }

    public Call<AccessToken> getLoginWithSMSVerification(String mobilePhone, String verificationCode, String invitationCode) {
        String vc = "";
        if (!TextUtils.isEmpty(mobilePhone) && !TextUtils.isEmpty(verificationCode)) {
            vc = mobilePhone + "-" + verificationCode;
        }
        if (!TextUtils.isEmpty(invitationCode)) vc += "-" + invitationCode;
        return getApi().getToken(ApiStatics.GRAND_TYPE_VERIFY, vc, ApiStatics.getClientId(), ApiStatics.getClientSecret(), "");
    }

    public Call<AccessToken> guestLogin() {
        EtkaPushNotificationConfig.subscribeGuests();
        return getApi().getToken(ApiStatics.GRAND_TYPE_PASSWORD, ApiStatics.getGuestUserName(), ApiStatics.getGuestPassword(), ApiStatics.getClientId(), ApiStatics.getClientSecret(), "");
    }

    public CertificatePinner certificatePinner = null;

    private CertificatePinner getPinnedCertificate() {
        if (certificatePinner == null && ApiStatics.getBaseUrl().contains("https://")) {
            certificatePinner = new CertificatePinner.Builder()
                    .add(ApiStatics.getBaseUrl().replace("https://", ""), "sha256/8ld6zKiNGWinT3KNSS+UMMPXOJrnATh9IFdFT9udNG8=")//8ld6zKiNGWinT3KNSS+UMMPXOJrnATh9IFdFT9udNG8=
                    .build();
        }
        return certificatePinner;
    }

    private void addTLSSocketFactory(OkHttpClient.Builder httpBuilder) {
        TLSSocketFactory tlsSocketFactory;
        try {
            tlsSocketFactory = new TLSSocketFactory();
            httpBuilder.sslSocketFactory(tlsSocketFactory, tlsSocketFactory.systemDefaultTrustManager());
        } catch (Exception err) {
            err.printStackTrace();
        }
    }

    private void setTimeOuts(OkHttpClient.Builder httpClient) {
        httpClient.connectTimeout(30, TimeUnit.SECONDS);
        httpClient.readTimeout(30, TimeUnit.SECONDS);
        httpClient.writeTimeout(30, TimeUnit.SECONDS);
    }

}
