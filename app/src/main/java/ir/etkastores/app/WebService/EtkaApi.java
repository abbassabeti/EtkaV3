package ir.etkastores.app.WebService;

import java.util.List;

import ir.etkastores.app.Models.CategoryModel;
import ir.etkastores.app.Models.Factor.FactorModel;
import ir.etkastores.app.Models.Factor.FactorRequestModel;
import ir.etkastores.app.Models.OauthResponse;
import ir.etkastores.app.Models.ProductModel;
import ir.etkastores.app.Models.ProductSearchResponseModel;
import ir.etkastores.app.Models.SearchProductRequestModel;
import ir.etkastores.app.Models.UserProfileModel;
import ir.etkastores.app.Models.hekmat.HekmatModel;
import ir.etkastores.app.Models.profile.RegisterUserRequestModel;
import ir.etkastores.app.Models.profile.ResetPasswordRequestModel;
import ir.etkastores.app.Models.store.StoreModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Sajad on 9/3/17.
 */

public interface EtkaApi {

    //1
    @FormUrlEncoded
    @POST(ApiStatics.TOKEN)
    Call<AccessToken> getToken(@Field("grant_type")String grant_type,
                               @Field("username")String username,
                               @Field("password")String password,
                               @Field("client_id")String client_id,
                               @Field("client_secret")String client_secret,
                               @Field("refresh_token") String refreshToken);
    //2
    @POST(ApiStatics.REGISTER)
    Call<OauthResponse<String>> registerNewUser(@Body RegisterUserRequestModel userRequestModel);

    //3
    @POST(ApiStatics.EDIT_PROFILE)
    Call<OauthResponse<String>> editUserProfile(@Body UserProfileModel userProfileModel);

    //4
    @POST(ApiStatics.RESET_PASSWORD)
    Call<OauthResponse<String>> resetPassword(@Body ResetPasswordRequestModel resetPasswordRequestModel);

    //5
    @GET(ApiStatics.PRODUCT_CATEGORY_CHILD)
    Call<OauthResponse<List<CategoryModel>>> getCategory(@Query("ParentId") long parentId);

    //6
    @GET(ApiStatics.PRODUCT_CATEGORY_LEVEL)
    Call<OauthResponse<List<CategoryModel>>> getCategoryAtLevel(@Query("Level") int id);

    //7
    @POST(ApiStatics.SEARCH)
    Call<OauthResponse<ProductSearchResponseModel>> searchProduct(@Body SearchProductRequestModel request);

    //8
    @GET(ApiStatics.PRODUCT)
    Call<OauthResponse<ProductModel>> getProductById(@Query("id") long id);

    //9
    @GET(ApiStatics.PRODUCT)
    Call<OauthResponse<ProductModel>> getProductByBarcode(@Query("Barcode") String barcode);

    //10
    @GET(ApiStatics.STORE)
    Call<OauthResponse<List<StoreModel>>> getStores();

    //11
    @GET(ApiStatics.PROFILE)
    Call<OauthResponse<UserProfileModel>> getUserProfile(@Query("UserId") String userId);

    //12
    @POST(ApiStatics.FACTOR)
    Call<OauthResponse<List<FactorModel>>> getFactor(@Body FactorRequestModel requestModel);

    //13
    @GET(ApiStatics.HEKMAT)
    Call<OauthResponse<List<HekmatModel>>> getHekmat();

}
