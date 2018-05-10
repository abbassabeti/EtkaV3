package ir.etkastores.app.models;

import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by garshasbi on 4/3/18.
 */

public class GalleryItemsModel {

    @SerializedName("title")
    String title;

    @SerializedName("images")
    List<String> images;

    @SerializedName("position")
    int position;

    public static GalleryItemsModel fromJson(String json){
        try {
            return new Gson().fromJson(json,GalleryItemsModel.class);
        }catch (Exception err){
            return null;
        }
    }

    public GalleryItemsModel(String title, List<String> images, int position) {
        this.title = title;
        this.images = images;
        this.position = position;
    }

    public GalleryItemsModel(String title,String image, int position){
        images = new ArrayList<>();
        images.add(image);
        this.title = title;
        this.position = position;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getImages() {
        return images;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String toJson(){
        try {
            return new Gson().toJson(this);
        }catch (Exception err){
            return "";
        }
    }

    public List<String> copyOfImages(){
        List<String> result = new ArrayList<>();
        if (getImages() == null || getImages().size() == 0) return result;
        for (String s : getImages()){
            result.add(s);
        }
        Collections.reverse(result);
        return result;
    }

}
