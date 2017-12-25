package ir.etkastores.app.WebService;

import java.util.List;

import ir.etkastores.app.Models.CategoryModel;
import ir.etkastores.app.Models.OauthResponse;
import ir.etkastores.app.Models.ProductModel;
import ir.etkastores.app.Models.SearchProductRequestModel;
import ir.etkastores.app.Models.UserProfileModel;
import ir.etkastores.app.Models.profile.RegisterUserRequestModel;
import ir.etkastores.app.Models.profile.ResetPasswordRequestModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

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

    @GET(ApiStatics.END_POINT_PRODUCT_CATEGORY_CHILD)
    Call<OauthResponse<List<CategoryModel>>> getCategory(@Query("ParentId") long parentId);

    @GET(ApiStatics.END_POINT_PRODUCT_CATEGORY_LEVEL)
    Call<OauthResponse<List<CategoryModel>>> getCategoryAtLevel(@Query("Level") int id);

    @GET(ApiStatics.END_POINT_PRODUCT_SEARCH)
    Call<OauthResponse<List<ProductModel>>> searchProduct(@Body SearchProductRequestModel request);

    @GET(ApiStatics.END_POINT_PRODUCT)
    Call<OauthResponse<ProductModel>> getProduct(@Query("id") long id,@Query("Barcode") String barcode);

}
