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

    boolean isSelected;

    public CategoryModel(String title,long id) {
        this.id = id;
        this.title = title;
        this.isSelected = isSelected;
    }

    public CategoryModel(String title,long id , boolean isSelected) {
        this.id = id;
        this.title = title;
        this.isSelected = isSelected;
    }

    public CategoryModel(long id, String title, int level, String imageUrl, long parentId, boolean hasChild, boolean isSelected) {
        this.id = id;
        this.title = title;
        this.level = level;
        this.imageUrl = imageUrl;
        this.parentId = parentId;
        this.hasChild = hasChild;
        this.isSelected = isSelected;
    }

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

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public CategoryModel getCopy(){
        return new CategoryModel(id,title,level,imageUrl,parentId,hasChild,isSelected);
    }

    public void toggle(){
        isSelected = (!isSelected);
    }
}
