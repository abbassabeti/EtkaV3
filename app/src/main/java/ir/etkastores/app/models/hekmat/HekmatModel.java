package ir.etkastores.app.models.hekmat;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Sajad on 1/21/18.
 */

public class HekmatModel {

    @SerializedName("id")
    private long id;

    @SerializedName("title")
    private String title;

    @SerializedName("stage")
    private String stage;

    @SerializedName("kalaCode")
    private String kalaCode;

    @SerializedName("startDate")
    private String startDate;

    @SerializedName("endDate")
    private String endDate;

    @SerializedName("imageUrl")
    private String imageUrl;

    @SerializedName("products")
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
