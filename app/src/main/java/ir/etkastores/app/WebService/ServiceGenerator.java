package ir.etkastores.app.WebService;


import java.io.IOException;

import ir.etkastores.app.Utils.DiskDataHelper;
import okhttp3.Authenticator;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static ir.etkastores.app.WebService.ApiStatics.BASE_URL;

/**
 * Created by Sajad on 9/3/17.
 */

public class ServiceGenerator {

    private static OkHttpClient.Builder httpClient;
    private static Retrofit.Builder builder;

    public static TokenResponse lastToken;

    public static <S> S createService(Class<S> serviceClass) {
        httpClient = new OkHttpClient.Builder();
        builder = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());

        OkHttpClient client = httpClient.build();
        Retrofit retrofit = builder.client(client).build();
        return retrofit.create(serviceClass);
    }

    public static <S> S createService(Class<S> serviceClass, TokenResponse accessToken) {
        httpClient = new OkHttpClient.Builder();
        builder = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());

        if (accessToken == null){
            accessToken = new TokenResponse();
        }

        if(accessToken != null) {
            lastToken = accessToken;
            final TokenResponse token = accessToken;
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
                    Call<TokenResponse> call = tokenClient.getToken("password",
                            "USER_NAME",
                            "PASSWORD",
                            ApiStatics.CLIENT_ID,
                            ApiStatics.CLINET_SECRET);
                    try {
                        retrofit2.Response<TokenResponse> tokenResponse = call.execute();
                        if(tokenResponse.code() == 200) {
                            TokenResponse newToken = tokenResponse.body();
                            lastToken = newToken;
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

}
