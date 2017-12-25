package ir.etkastores.app.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Sajad on 12/23/17.
 */

public class CategoryModel {

    @SerializedName("id")
    long id;

    @SerializedName("title")
    String title;

    @SerializedName("level")
    int level;

    @SerializedName("imageUrl")
    String imageUrl;

    @SerializedName("parentId")
    long parentId;

    @SerializedName("hasChild")
    boolean hasChild;

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getLevel() {
        return level;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public long getParentId() {
        return parentId;
    }

    public boolean isHasChild() {
        return hasChild;
    }
}
