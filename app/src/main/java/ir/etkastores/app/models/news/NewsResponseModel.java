package ir.etkastores.app.models.news;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by garshasbi on 3/27/18.
 */

public class NewsResponseModel {

    @SerializedName("totalItemsCount")
    private int totalItemsCount;

    @SerializedName("items")
    private List<NewsItem> items;

    public int getTotalItemsCount() {
        return totalItemsCount;
    }

    public List<NewsItem> getItems() {
        return items;
    }

}
