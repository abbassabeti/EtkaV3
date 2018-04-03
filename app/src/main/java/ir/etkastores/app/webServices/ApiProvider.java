package ir.etkastores.app.webServices;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import ir.etkastores.app.BuildConfig;
import ir.etkastores.app.models.OauthResponse;
import ir.etkastores.app.utils.DiskDataHelper;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.Buffer;
import okio.BufferedSource;
import okio.GzipSource;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.google.zxing.common.CharacterSetECI.UTF8;


/**
 * Created by Sajad on 9/3/17.
 */

public class ApiProvider {

    private static OkHttpClient.Builder httpClient;
    private static Retrofit.Builder builder;

    public static AccessToken lastToken;

    public static <S> S createService(Class<S> serviceClass) {
        httpClient = new OkHttpClient.Builder();
        builder = new Retrofit.Builder()
                .baseUrl(ApiStatics.getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create());

        if (BuildConfig.DEBUG) {
            httpClient.addInterceptor(getBodyLogInterceptor());
            httpClient.addInterceptor(getHeadersLogInterceptor());
        }

        OkHttpClient client = httpClient.build();
        Retrofit retrofit = builder.client(client).build();
        return retrofit.create(serviceClass);
    }

    public static <S> S createService(Class<S> serviceClass, AccessToken accessToken) {
        httpClient = new OkHttpClient.Builder();
        builder = new Retrofit.Builder()
                .baseUrl(ApiStatics.getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create());

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

                    BufferedSource source = proceed.body().source();
                    source.request(Long.MAX_VALUE); // Buffer the entire body.
                    Buffer buffer = source.buffer();

                    Headers headers = proceed.headers();
                    Long gzippedLength = null;
                    if ("gzip".equalsIgnoreCase(headers.get("Content-Encoding"))) {
                        gzippedLength = buffer.size();
                        GzipSource gzippedResponseBody = null;
                        try {
                            gzippedResponseBody = new GzipSource(buffer.clone());
                            buffer = new Buffer();
                            buffer.writeAll(gzippedResponseBody);
                        } finally {
                            if (gzippedResponseBody != null) {
                                gzippedResponseBody.close();
                            }
                        }
                    }

                    final Charset UTF8 = Charset.forName("UTF-8");
                    Charset charset = UTF8;
                    MediaType contentType = proceed.body().contentType();
                    if (contentType != null) {
                        charset = contentType.charset(UTF8);
                    }
                    String jsonResponse = buffer.clone().readString(charset);
                    OauthResponse<Object> response = new Gson().fromJson(jsonResponse, new TypeToken<OauthResponse<Object>>() {
                    }.getType());
                    if (response != null && response.getMeta() != null && response.getMeta().getStatusCode() == 401) {
                        synchronized (httpClient) {

                            String refreshToken = ApiStatics.getLastToken().getRefreshToken();
                            lastToken = null;
                            ApiStatics.saveToken(null);
                            EtkaApi tokenClient = createService(EtkaApi.class);
                            Call<AccessToken> call = tokenClient.getToken(
                                    ApiStatics.GRAND_TYPE_REFRESH_TOKEN,
                                    "",
                                    "",
                                    ApiStatics.CLIENT_ID,
                                    "",
                                    refreshToken);

                            retrofit2.Response<AccessToken> tokenResponse = call.execute();
                            if (tokenResponse.code() == 200) {
                                AccessToken newToken = tokenResponse.body();
                                lastToken = newToken;
                                ApiStatics.saveToken(lastToken);
                                request=request.newBuilder()
                                .header("Authorization", newToken.getTokenType() + " " + newToken.getAccessToken())
                                .build();
                                proceed = chain.proceed(request);
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

        httpClient.connectTimeout(20, TimeUnit.SECONDS);
        httpClient.readTimeout(20, TimeUnit.SECONDS);
        httpClient.writeTimeout(20, TimeUnit.SECONDS);
        OkHttpClient client = httpClient.build();
        Retrofit retrofit = builder.client(client).build();
        return retrofit.create(serviceClass);
    }

    public static EtkaApi getApi() {
        return createService(EtkaApi.class);
    }

    public static EtkaApi getAuthorizedApi() {
        return createService(EtkaApi.class, DiskDataHelper.getLastToken());
    }

    private static HttpLoggingInterceptor getBodyLogInterceptor() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        return logging;
    }

    private static HttpLoggingInterceptor getHeadersLogInterceptor() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        return logging;
    }

    public static Call<AccessToken> getLogin(String userName, String password) {
        return getApi().getToken(ApiStatics.GRAND_TYPE_PASSWORD, userName, password, ApiStatics.CLIENT_ID, ApiStatics.CLIENT_SECRET, "");
    }

}
