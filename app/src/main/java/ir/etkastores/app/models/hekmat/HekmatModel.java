package ir.etkastores.app.models.hekmat;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
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

    public HekmatModel getCopy(){
        HekmatModel hekmatModel = new HekmatModel();
        hekmatModel.id = id;
        hekmatModel.title = title;
        hekmatModel.stage = stage;
        hekmatModel.kalaCode = kalaCode;
        hekmatModel.startDate = startDate;
        hekmatModel.endDate = endDate;
        hekmatModel.imageUrl = imageUrl;
        hekmatModel.products = new ArrayList<>();
        for (HekmatProductModel hekmatProductModel : products){
            hekmatModel.products.add(HekmatProductModel.fromJson(new Gson().toJson(hekmatProductModel)));
        }
        return hekmatModel;
    }
}
