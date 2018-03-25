package ir.etkastores.app.Models.saveProduct;

import com.google.gson.annotations.SerializedName;

/**
 * Created by garshasbi on 3/25/18.
 */

public class SaveProductRequestModel {

    @SerializedName("ProductId")
    private long productId;

    @SerializedName("Count")
    private int count;

    public SaveProductRequestModel(long productId, int count) {
        this.productId = productId;
        this.count = count;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public long getProductId() {
        return productId;
    }

    public int getCount() {
        return count;
    }
}
