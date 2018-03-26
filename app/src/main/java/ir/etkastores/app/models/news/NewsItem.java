package ir.etkastores.app.models.news;

import com.google.gson.annotations.SerializedName;

/**
 * Created by garshasbi on 3/27/18.
 */

public class NewsItem {

    @SerializedName("id")
    private int id;

    @SerializedName("title")
    private String title;

    @SerializedName("content")
    private String content;

    @SerializedName("date")
    private String date;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getDate() {
        return date;
    }

}
