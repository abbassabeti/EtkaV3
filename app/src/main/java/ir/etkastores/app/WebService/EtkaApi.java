package ir.etkastores.app.WebService;

import ir.etkastores.app.Models.OauthResponse;
import ir.etkastores.app.Models.UserProfileModel;
import ir.etkastores.app.Models.profile.RegisterUserRequestModel;
import ir.etkastores.app.Models.profile.ResetPasswordRequestModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Sajad on 9/3/17.
 */

public interface EtkaApi {

    @FormUrlEncoded
    @POST(ApiStatics.END_POINT_TOKEN)
    Call<AccessToken> getToken(@Field("grant_type")String grant_type,
                               @Field("username")String username,
                               @Field("password")String password,
                               @Field("client_id")String client_id,
                               @Field("client_secret")String client_secret,
                               @Field("refresh_token") String refreshToken);

    @POST(ApiStatics.END_POINT_REGISTER)
    Call<OauthResponse<String>> registerNewUser(@Body RegisterUserRequestModel userRequestModel);

    @POST(ApiStatics.END_POINT_EDIT_PROFILE)
    Call<OauthResponse<String>> editUserProfile(@Body UserProfileModel userProfileModel);

    @POST(ApiStatics.END_POINT_RESET_PASSWORD)
    Call<OauthResponse<String>> resetPassword(@Body ResetPasswordRequestModel resetPasswordRequestModel);

}
