package ir.etkastores.app.WebService;

import java.util.List;

import ir.etkastores.app.Models.CategoryModel;
import ir.etkastores.app.Models.Factor.FactorModel;
import ir.etkastores.app.Models.Factor.FactorRequestModel;
import ir.etkastores.app.Models.OauthResponse;
import ir.etkastores.app.Models.ProductModel;
import ir.etkastores.app.Models.SearchProductRequestModel;
import ir.etkastores.app.Models.UserProfileModel;
import ir.etkastores.app.Models.profile.RegisterUserRequestModel;
import ir.etkastores.app.Models.profile.ResetPasswordRequestModel;
import ir.etkastores.app.Models.store.StoreModel;
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

    //1
    @FormUrlEncoded
    @POST(ApiStatics.END_POINT_TOKEN)
    Call<AccessToken> getToken(@Field("grant_type")String grant_type,
                               @Field("username")String username,
                               @Field("password")String password,
                               @Field("client_id")String client_id,
                               @Field("client_secret")String client_secret,
                               @Field("refresh_token") String refreshToken);
    //2
    @POST(ApiStatics.END_POINT_REGISTER)
    Call<OauthResponse<String>> registerNewUser(@Body RegisterUserRequestModel userRequestModel);

    //3
    @POST(ApiStatics.END_POINT_EDIT_PROFILE)
    Call<OauthResponse<String>> editUserProfile(@Body UserProfileModel userProfileModel);

    //4
    @POST(ApiStatics.END_POINT_RESET_PASSWORD)
    Call<OauthResponse<String>> resetPassword(@Body ResetPasswordRequestModel resetPasswordRequestModel);

    //5
    @GET(ApiStatics.END_POINT_PRODUCT_CATEGORY_CHILD)
    Call<OauthResponse<List<CategoryModel>>> getCategory(@Query("ParentId") long parentId);

    //6
    @GET(ApiStatics.END_POINT_PRODUCT_CATEGORY_LEVEL)
    Call<OauthResponse<List<CategoryModel>>> getCategoryAtLevel(@Query("Level") int id);

    //7
    @GET(ApiStatics.END_POINT_PRODUCT_SEARCH)
    Call<OauthResponse<List<ProductModel>>> searchProduct(@Body SearchProductRequestModel request);

    //8`
    @GET(ApiStatics.END_POINT_PRODUCT)
    Call<OauthResponse<ProductModel>> getProduct(@Query("id") long id,@Query("Barcode") String barcode);

    //9
    @GET(ApiStatics.END_POINT_STORE)
    Call<OauthResponse<List<StoreModel>>> getStores();

    //10
    @GET(ApiStatics.END_POINT_USER_PROFILE)
    Call<OauthResponse<UserProfileModel>> getUserProfile();

    //12
    @POST(ApiStatics.END_POINT_FACTOR)
    Call<OauthResponse<FactorModel>> getFactor(@Body FactorRequestModel requestModel);


}
