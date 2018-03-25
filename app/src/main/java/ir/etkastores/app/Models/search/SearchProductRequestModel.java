package ir.etkastores.app.Models.search;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Sajad on 12/25/17.
 */

public class SearchProductRequestModel {

    public static SearchProductRequestModel fromJson(String json){
        try {
            return  new Gson().fromJson(json,SearchProductRequestModel.class);
        }catch (Exception err){
            return null;
        }
    }

    public static class Sorts{
        public final static String UpdateDateAsc = "UpdateDateAsc";
        public final static String UpdateDateDesc = "UpdateDateDesc";
        public final static String PriceAsc = "PriceAsc";
        public final static String PriceDesc = "PriceDesc";
        public final static String PointAsc = "String";
        public final static String PointDesc = "PointDesc";
    }

    @SerializedName("Title")
    String title;

    @SerializedName("CategoryId")
    Long categoryId;

    @SerializedName("Sort")
    String sort;

    @SerializedName("Take")
    int take;

    @SerializedName("Page")
    int page;

    public SearchProductRequestModel() {
        page = 1;
    }

    public SearchProductRequestModel setTitle(String title) {
        this.title = title;
        return this;
    }

    public SearchProductRequestModel setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
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

    public int getPage() {
        return page;
    }

    public String getTitle() {
        return title;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public String getSort() {
        return sort;
    }

    public int getTake() {
        return take;
    }

}
