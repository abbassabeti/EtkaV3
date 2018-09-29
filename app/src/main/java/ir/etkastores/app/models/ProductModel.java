package ir.etkastores.app.models;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Sajad on 12/25/17.
 */

public class ProductModel {

    @SerializedName("id")
    long id;

    @SerializedName("code")
    String code;

    @SerializedName("barCode")
    String barCode;

    @SerializedName("title")
    String title;

    @SerializedName("description")
    String description;

    @SerializedName("imageUrl")
    List<String> imageUrl;

    @SerializedName("originalPrice")
    String originalPrice;

    @SerializedName("etkaPrice")
    String etkaPrice;

    @SerializedName("offerPrice")
    String offerPrice;

    @SerializedName("categoryTitle")
    String categoryTitle;

    @SerializedName("supplierName")
    String supplierName;

    @SerializedName("Point")
    int point;

    @SerializedName("proprietaryPoint")
    int proprietaryPoint;

    @SerializedName("discountPercentage")
    int discountPercentage;

    @SerializedName("savedCount")
    int savedCount;

    @SerializedName("relatedProducts")
    private List<ProductModel> relatedProducts;

    @SerializedName("relatedIsFake")
    boolean relatedIsFake = false;

    @SerializedName("categoryId")
    private long categoryId;

    public static ProductModel fromJson(String json) {
        try {
            return new Gson().fromJson(json, ProductModel.class);
        } catch (Exception err) {
            return null;
        }
    }

    public ProductModel(long id, String code, String barCode, String title, String description, List<String> imageUrl, String originalPrice, String etkaPrice, String offerPrice, String categoryTitle, String supplierName, int point, int proprietaryPoint, int discountPercentage, int savedCount, boolean relatedIsFake, long categoryId) {
        this.id = id;
        this.code = code;
        this.barCode = barCode;
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.originalPrice = originalPrice;
        this.etkaPrice = etkaPrice;
        this.offerPrice = offerPrice;
        this.categoryTitle = categoryTitle;
        this.supplierName = supplierName;
        this.point = point;
        this.proprietaryPoint = proprietaryPoint;
        this.discountPercentage = discountPercentage;
        this.savedCount = savedCount;
        this.relatedIsFake = relatedIsFake;
        this.categoryId = categoryId;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setOriginalPrice(String originalPrice) {
        this.originalPrice = originalPrice;
    }

    public void setEtkaPrice(String etkaPrice) {
        this.etkaPrice = etkaPrice;
    }

    public void setOfferPrice(String offerPrice) {
        this.offerPrice = offerPrice;
    }

    public void setImageUrl(List<String> imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public void setProprietaryPoint(int proprietaryPoint) {
        this.proprietaryPoint = proprietaryPoint;
    }

    public long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getBarCode() {
        return barCode;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getImageUrl() {
        return imageUrl;
    }

    public String getOriginalPrice() {
        if (originalPrice == null) return "";
        return originalPrice;
    }

    public String getEtkaPrice() {
        if (etkaPrice == null) return "";
        return etkaPrice;
    }

    public String getOfferPrice() {
        return offerPrice;
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public String getPoint() {
        if (point > 0) {
            return String.valueOf(point);
        } else {
            return "";
        }
    }

    public int getProprietaryPoint() {
        return proprietaryPoint;
    }

    public int getDiscountPercentage() {
        return discountPercentage;
    }

    public String getFinalPrice() {
        if (!TextUtils.isEmpty(getOfferPrice())) {
            return getOfferPrice();
        }
        if (!TextUtils.isEmpty(getEtkaPrice())) {
            return getEtkaPrice();
        }
        if (!TextUtils.isEmpty(getOriginalPrice())) {
            return getOriginalPrice();
        }
        return "";
    }

    public String getStrikeThruPrice() {
        if (!TextUtils.isEmpty(getOfferPrice()) && !TextUtils.isEmpty(getEtkaPrice())) {
            return getEtkaPrice();
        }
        if (!TextUtils.isEmpty(getOriginalPrice())) {
            return getOriginalPrice();
        }
        return "";
    }

    public int getSavedCount() {
        if (savedCount == 0) savedCount = 1;
        return savedCount;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public List<ProductModel> getRelatedProducts() {
        return relatedProducts;
    }

    //handle fake related products ;)
    public void setRelatedProducts(List<ProductModel> relatedProducts) {
//        if (this.relatedProducts != null && this.relatedProducts.size() > 0 && relatedIsFake == false){
//            return;
//        }
//
//        if (relatedProducts == null || relatedProducts.size() == 0) {
//            this.relatedProducts = new ArrayList<>();
//            return;
//        }
//        relatedIsFake = true;
//        List<ProductModel> result = new ArrayList<>();
//        int x = 0;
//        for (ProductModel productModel : relatedProducts) {
//            if (productModel.getId() == getId()) {
//                continue;
//            }
//            if (x < 15) {
//                result.add(productModel.getCopy());
//                x++;
//            } else {
//                break;
//            }
//        }
//        Collections.shuffle(result);
//        this.relatedProducts = result;
    }

    public ProductModel getCopy() {
        return new ProductModel(id, code, barCode, title, description, imageUrl, originalPrice, etkaPrice, offerPrice, categoryTitle, supplierName, point, proprietaryPoint, discountPercentage, savedCount, relatedIsFake, categoryId);
    }

}
