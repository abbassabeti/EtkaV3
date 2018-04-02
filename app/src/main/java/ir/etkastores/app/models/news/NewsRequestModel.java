package ir.etkastores.app.models.news;

import com.google.gson.annotations.SerializedName;

/**
 * Created by garshasbi on 3/27/18.
 */

public class NewsRequestModel {

    @SerializedName("Title")
    private String title;

    @SerializedName("Page")
    private int page;

    public NewsRequestModel(String title, int page) {
        this.title = title;
        this.page = page;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getTitle() {
        return title;
    }

    public int getPage() {
        return page;
    }
}
