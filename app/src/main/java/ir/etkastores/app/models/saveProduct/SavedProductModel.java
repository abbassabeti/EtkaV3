package ir.etkastores.app.models.saveProduct;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import ir.etkastores.app.models.ProductModel;

/**
 * Created by garshasbi on 3/25/18.
 */

public class SavedProductModel {

    @SerializedName("Product")
    private List<ProductModel> product;

    @SerializedName("Count")
    private int count;

    public List<ProductModel> getProduct() {
        return product;
    }

    public int getCount() {
        return count;
    }

}
