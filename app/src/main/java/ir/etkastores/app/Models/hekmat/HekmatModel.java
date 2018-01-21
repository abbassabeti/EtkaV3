package ir.etkastores.app.Models.hekmat;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Sajad on 1/21/18.
 */

public class HekmatModel {

    @SerializedName("Id")
    private long id;

    @SerializedName("Title")
    private String title;

    @SerializedName("Stage")
    private String stage;

    @SerializedName("KalaCode")
    private String kalaCode;

    @SerializedName("StartDate")
    private String startDate;

    @SerializedName("EndDate")
    private String endDate;

    @SerializedName("ImageUrl")
    private String imageUrl;

    @SerializedName("Products")
    private List<HekmatProductModel> products;

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getStage() {
        return stage;
    }

    public String getKalaCode() {
        return kalaCode;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public List<HekmatProductModel> getProducts() {
        return products;
    }
}
