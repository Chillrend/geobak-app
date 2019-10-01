
package org.geobak.geobakapp.model.favorite;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Result {

    @SerializedName("name_product")
    @Expose
    private String nameProduct;
    @SerializedName("name_seller")
    @Expose
    private String nameSeller;
    @SerializedName("price_unit")
    @Expose
    private String priceUnit;

    @SerializedName("rating")
    @Expose
    private String rating;

    @SerializedName("latitude")
    @Expose
    private String latitude;

    @SerializedName("longitude")
    @Expose
    private String longitude;

    @SerializedName("image")
    @Expose
    private String image;

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public String getNameSeller() {
        return nameSeller;
    }

    public void setNameSeller(String nameSeller) {
        this.nameSeller = nameSeller;
    }

    public String getPriceUnit() {
        return priceUnit;
    }

    public void setPriceUnit(String priceUnit) {
        this.priceUnit = priceUnit;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Result(String nameProduct, String nameSeller, String priceUnit, String rating, String latitude, String longitude, String image) {
        this.nameProduct = nameProduct;
        this.nameSeller = nameSeller;
        this.priceUnit = priceUnit;
        this.rating = rating;
        this.latitude = latitude;
        this.longitude = longitude;
        this.image = image;
    }

    public static List<Result> emulateApiCall(){
        List<Result> res = new ArrayList<>();

        res.add(new Result("Sate Babiq", "Wawa", "10000", "4.0", "-6.453536", "106.839529", ""));
        res.add(new Result("Sate Ayam", "Awe", "15000", "3.0", "-6.458507", "106.839329", ""));

        return res;
    }
}
