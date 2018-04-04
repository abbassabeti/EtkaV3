package ir.etkastores.app.webServices;

import java.util.List;

import ir.etkastores.app.models.CategoryModel;
import ir.etkastores.app.models.factor.FactorModel;
import ir.etkastores.app.models.factor.FactorRequestModel;
import ir.etkastores.app.models.OauthResponse;
import ir.etkastores.app.models.ProductModel;
import ir.etkastores.app.models.news.NewsRequestModel;
import ir.etkastores.app.models.news.NewsResponseModel;
import ir.etkastores.app.models.search.ProductSearchResponseModel;
import ir.etkastores.app.models.search.SearchProductRequestModel;
import ir.etkastores.app.models.profile.UserProfileModel;
import ir.etkastores.app.models.hekmat.HekmatModel;
import ir.etkastores.app.models.home.OffersResponseModel;
import ir.etkastores.app.models.profile.RegisterUserRequestModel;
import ir.etkastores.app.models.profile.ChangePasswordRequestModel;
import ir.etkastores.app.models.saveProduct.SaveProductRequestModel;
import ir.etkastores.app.models.store.StoreModel;
import ir.etkastores.app.models.tickets.TicketRequestModel;
import ir.etkastores.app.models.tickets.TicketResponseModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
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
    @POST(ApiStatics.TOKEN)
    Call<AccessToken> getToken(@Field("grant_type") String grant_type,
                               @Field("username") String username,
                               @Field("password") String password,
                               @Field("client_id") String client_id,
                               @Field("client_secret") String client_secret,
                               @Field("refresh_token") String refreshToken);

    //2
    @POST(ApiStatics.REGISTER)
    Call<OauthResponse<String>> registerNewUser(@Body RegisterUserRequestModel userRequestModel);

    //3
    @POST(ApiStatics.EDIT_PROFILE)
    Call<OauthResponse<String>> editUserProfile(@Body UserProfileModel userProfileModel);

    //4
    @GET(ApiStatics.PRODUCT_CATEGORY_CHILD)
    Call<OauthResponse<List<CategoryModel>>> getCategory(@Query("ParentId") long parentId);

    //5
    @GET(ApiStatics.PRODUCT_CATEGORY_LEVEL)
    Call<OauthResponse<List<CategoryModel>>> getCategoryAtLevel(@Query("Level") int id);

    //6
    @POST(ApiStatics.SEARCH)
    Call<OauthResponse<ProductSearchResponseModel>> searchProduct(@Body SearchProductRequestModel request);

    //7
    @GET(ApiStatics.PRODUCT)
    Call<OauthResponse<ProductModel>> getProductById(@Query("id") long id);

    //8
    @GET(ApiStatics.PRODUCT)
    Call<OauthResponse<ProductModel>> getProductByBarcode(@Query("Barcode") String barcode);

    //9
    @GET(ApiStatics.STORE)
    Call<OauthResponse<List<StoreModel>>> getStores();

    //10
    @GET(ApiStatics.PROFILE)
    Call<OauthResponse<UserProfileModel>> getUserProfile(@Query("UserId") String userId);

    //11
    @POST(ApiStatics.FACTOR)
    Call<OauthResponse<List<FactorModel>>> getFactor(@Body FactorRequestModel requestModel);

    //12
    @GET(ApiStatics.HEKMAT)
    Call<OauthResponse<List<HekmatModel>>> getHekmat();

    //13
    @GET(ApiStatics.HOME)
    Call<OauthResponse<OffersResponseModel>> getOffers(@Query("page") String page);

    //14
    @POST(ApiStatics.CHANGE_PASSWORD)
    Call<OauthResponse<String>> changePassword(@Body ChangePasswordRequestModel resetPasswordRequestModel);

    //15
    @POST(ApiStatics.SEND_TICKET)
    Call<OauthResponse<Long>> sendTicket(@Body TicketRequestModel requestModel);

    //16
    @POST(ApiStatics.SAVE_PRODUCTS)
    Call<OauthResponse<Long>> saveProduct(@Body SaveProductRequestModel requestModel);

    //17
    @DELETE(ApiStatics.SAVE_PRODUCTS)
    Call<OauthResponse<Long>> deleteSavedProduct(@Query("id") long id);

    //18
    @GET(ApiStatics.SAVE_PRODUCTS)
    Call<OauthResponse<List<ProductModel>>> getSavedProducts();

    //19
    @GET(ApiStatics.GET_TICKET)
    Call<OauthResponse<TicketResponseModel>> getTickets(@Query("page") int page);

    //20
    @POST(ApiStatics.NEWS)
    Call<OauthResponse<NewsResponseModel>> getNews(@Body NewsRequestModel requestModel);

    //21
    @POST(ApiStatics.RESET_PASSWORD)
    Call<OauthResponse<String>> resetPassword(@Query("PhoneNumber") String phoneNumber);

}
