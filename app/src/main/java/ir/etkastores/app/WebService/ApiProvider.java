package ir.etkastores.app.WebService;


import java.io.IOException;

import ir.etkastores.app.BuildConfig;
import ir.etkastores.app.Utils.DiskDataHelper;
import okhttp3.Authenticator;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by Sajad on 9/3/17.
 */

public class ApiProvider {

    private static OkHttpClient.Builder httpClient;
    private static Retrofit.Builder builder;

    public static AccessToken lastToken;

    public static <S> S createService(Class<S> serviceClass) {
        httpClient = new OkHttpClient.Builder();
        if (BuildConfig.DEBUG){
            httpClient.addInterceptor(getBodyLogInterceptor());
            httpClient.addInterceptor(getHeadersLogInterceptor());
        }
        builder = new Retrofit.Builder()
                .baseUrl(ApiStatics.getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create());

        OkHttpClient client = httpClient.build();
        Retrofit retrofit = builder.client(client).build();
        return retrofit.create(serviceClass);
    }

    public static <S> S createService(Class<S> serviceClass, AccessToken accessToken) {
        httpClient = new OkHttpClient.Builder();
        if (BuildConfig.DEBUG){
            httpClient.addInterceptor(getBodyLogInterceptor());
            httpClient.addInterceptor(getHeadersLogInterceptor());
        }
        builder = new Retrofit.Builder()
                .baseUrl(ApiStatics.getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create());

        if (accessToken == null){
            accessToken = new AccessToken();
        }

        if(accessToken != null) {
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
                    return chain.proceed(request);
                }
            });

            httpClient.authenticator(new Authenticator() {
                @Override
                public Request authenticate(Route route, Response response) throws IOException {
                    if(responseCount(response) >= 2) {
                        return null;
                    }
                    EtkaApi tokenClient = createService(EtkaApi.class);
                    Call<AccessToken> call = tokenClient.getToken(
                            ApiStatics.GRAND_TYPE_REFRESH_TOKEN,
                            "",
                            "",
                            ApiStatics.CLIENT_ID,
                            "",
                            lastToken.getRefreshToken());
                    try {
                        retrofit2.Response<AccessToken> tokenResponse = call.execute();
                        if(tokenResponse.code() == 200) {
                            AccessToken newToken = tokenResponse.body();
                            lastToken = newToken;
                            ApiStatics.saveToken(newToken);
                            DiskDataHelper.saveLastToken(newToken);
                            return response.request().newBuilder()
                                    .header("Authorization", newToken.getTokenType() + " " + newToken.getAccessToken())
                                    .build();
                        } else {
                            return null;
                        }
                    } catch(IOException e) {
                        return null;
                    }
                }
            });
        }

        OkHttpClient client = httpClient.build();
        Retrofit retrofit = builder.client(client).build();
        return retrofit.create(serviceClass);
    }

    private static int responseCount(Response response) {
        int result = 1;
        while ((response = response.priorResponse()) != null) {
            result++;
        }
        return result;
    }

    public static EtkaApi getApi(){
        return createService(EtkaApi.class);
    }

    public static EtkaApi getAuthorizedApi(){
        return createService(EtkaApi.class,DiskDataHelper.getLastToken());
    }

    HttpLoggingInterceptor logging = new HttpLoggingInterceptor();

    private static HttpLoggingInterceptor getBodyLogInterceptor(){
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        return logging;
    }

    private static HttpLoggingInterceptor getHeadersLogInterceptor(){
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        return logging;
    }

    public static Call<AccessToken> getLogin(String userName, String password){
        return getApi().getToken(ApiStatics.GRAND_TYPE_PASSWORD,userName,password,ApiStatics.CLIENT_ID,ApiStatics.CLIENT_SECRET,"");
    }

}
