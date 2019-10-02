
package org.geobak.geobakapp.model.history;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Hasil {

    @SerializedName("name_product")
    @Expose
    private String nameProduct;
    @SerializedName("name_seller")
    @Expose
    private String nameSeller;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("date_transaction")
    @Expose
    private String dateTransaction;
    @SerializedName("time_transaction")
    @Expose
    private String timeTransaction;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDateTransaction() {
        return dateTransaction;
    }

    public void setDateTransaction(String dateTransaction) {
        this.dateTransaction = dateTransaction;
    }

    public String getTimeTransaction() {
        return timeTransaction;
    }

    public void setTimeTransaction(String timeTransaction) {
        this.timeTransaction = timeTransaction;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
