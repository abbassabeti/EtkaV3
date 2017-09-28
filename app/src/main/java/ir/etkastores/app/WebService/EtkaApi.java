package ir.etkastores.app.WebService;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Sajad on 9/3/17.
 */

public interface EtkaApi {

    @FormUrlEncoded
    @POST(ApiStatics.END_POINT_TOKEN)
    Call<TokenResponse> getToken(@Field("grant_type")String grant_type,
                                 @Field("username")String username,
                                 @Field("password")String password,
                                 @Field("client_id")String client_id,
                                 @Field("client_secret")String client_secret);

}
