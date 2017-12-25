package ir.etkastores.app.Models;

import com.google.gson.annotations.SerializedName;

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
    String imageUrl;

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

    @SerializedName("ProprietaryPoint")
    int proprietaryPoint;

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

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public String getOriginalPrice() {
        return originalPrice;
    }

    public String getEtkaPrice() {
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

    public int getPoint() {
        return point;
    }

    public int getProprietaryPoint() {
        return proprietaryPoint;
    }

}
