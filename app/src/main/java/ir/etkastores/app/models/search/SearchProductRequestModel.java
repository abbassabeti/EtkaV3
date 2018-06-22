package ir.etkastores.app.models.search;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sajad on 12/25/17.
 */

public class SearchProductRequestModel {

    @Expose
    @SerializedName("Title")
    String title;

    @Expose
    @SerializedName("CategoryId")
    List<Long> categoryId;

    @Expose
    @SerializedName("Sort")
    String sort;

    @Expose
    @SerializedName("Take")
    int take;

    @Expose
    @SerializedName("Page")
    int page;

    @Expose
    @SerializedName("Tags")
    List<String> tags;

    @Expose
    @SerializedName("StoreId")
    Long storeId;

    @Expose
    @SerializedName("SupplierId")
    Long supplierId;

    @Expose
    @SerializedName("OnlyEtkaProducts")
    boolean onlyEtkaProducts;

    public SearchProductRequestModel() {
        page = 1;
        categoryId = new ArrayList<>();
    }

    public SearchProductRequestModel setTitle(String title) {
        this.title = title;
        return this;
    }

    public SearchProductRequestModel addCategoryId(Long categoryId) {
        this.categoryId.add(categoryId);
        return this;
    }

    public SearchProductRequestModel setSort(String sort) {
        this.sort = sort;
        return this;
    }

    public SearchProductRequestModel setTake(int take) {
        this.take = take;
        return this;
    }

    public SearchProductRequestModel setPage(int page) {
        this.page = page;
        return this;
    }

    public void setCategoryId(List<Long> categoryId) {
        this.categoryId = categoryId;
    }

    public int getPage() {
        return page;
    }

    public String getTitle() {
        return title;
    }

    public String getSort() {
        return sort;
    }

    public int getTake() {
        return take;
    }

    public List<Long> getCategoryId() {
        return categoryId;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public boolean isOnlyEtkaProducts() {
        return onlyEtkaProducts;
    }

    public void setOnlyEtkaProducts(boolean onlyEtkaProducts) {
        this.onlyEtkaProducts = onlyEtkaProducts;
    }

    public static SearchProductRequestModel fromJson(String json){
        try {
            return  new Gson().fromJson(json,SearchProductRequestModel.class);
        }catch (Exception err){
            return null;
        }
    }

    public static class SortOptions {
        public final static String Default = "default";
        public final static String UpdateDateDesc = "UpdateDateDesc";
        public final static String PriceAsc = "PriceAsc";
        public final static String OfferPrice = "OfferPrice";
        public final static String PointDesc = "PointDesc";
    }

}
