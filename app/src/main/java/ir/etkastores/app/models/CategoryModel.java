package ir.etkastores.app.models;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Sajad on 12/23/17.
 */

public class CategoryModel {

    public static CategoryModel fromJson(String json){
        try {
            return new Gson().fromJson(json,CategoryModel.class);
        }catch (Exception err){
            return null;
        }
    }


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

    public boolean hasChild() {
        return hasChild;
    }
}
