package org.geobak.geobakapp.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Tenant implements Serializable {
    private String img_url;
    private String product;
    private String tenant_name;
    private String price;
    private Float rating;
    private Double lat;
    private Double lng;
    private String JSON_STRING;

    public Tenant(String img_url, String product, String tenant_name, String price, Float rating, Double lat, Double lng) {
        this.img_url = img_url;
        this.product = product;
        this.tenant_name = tenant_name;
        this.price = price;
        this.rating = rating;
        this.lat = lat;
        this.lng = lng;
    }

    public Tenant( String product, String tenant_name, String price, String rating) {
        this.img_url = img_url;
        this.product = product;
        this.tenant_name = tenant_name;
        this.price = price;
//        this.rating = rating;
//        this.lat = lat;
//        this.lng = lng;
    }


    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getTenant_name() {
        return tenant_name;
    }

    public void setTenant_name(String tenant_name) {
        this.tenant_name = tenant_name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }


    //EMULATE API CALL
    public static List<Tenant> populateTenantList(){
//        JSONObject jsonObject = null;
        List<Tenant> tenants = new ArrayList<>();
        tenants.add(new Tenant("https://picsum.photos/50/50", "Sate Ayam", "Pak Gembul", "Rp. 20.000", new Float(4.5),-6.371975, 106.824409));
        tenants.add(new Tenant("https://picsum.photos/50/50", "Sate Babiq", "Pak Wawa", "Rp. 15.000", new Float(1),-6.372255, 106.824127));
        tenants.add(new Tenant("https://picsum.photos/50/50", "Japanese Food", "Izakaya", "Rp. 15.000", new Float(5),-6.372020, 106.823561));
        tenants.add(new Tenant("https://picsum.photos/50/50", "Nasi Goreng Anter Lama", "Pak Hedar", "Rp. 15.000", new Float(5),-6.372428, 106.824135));

        return tenants;


//        try {
//            jsonObject = new JSONObject(JSON_STRING);
//            JSONArray result = jsonObject.getJSONArray(konfigurasi.TAG_JSON_ARRAY_STORY);
//
//            for (int i = 0; i < result.length(); i++) {
//                try {
//                    JSONObject jo = result.getJSONObject(i);
//                    String id = jo.getString(konfigurasi.TAG_ID_STORY);
//                    String product = jo.getString(konfigurasi.TAG_PRODUCT);
//                    String tenant_name = jo.getString(konfigurasi.TAG_TENANT_NAME);
//                    String price = jo.getString(konfigurasi.TAG_PRICE);
//                    String rating = jo.getString(konfigurasi.TAG_RATING);
//                    String lat = jo.getString(konfigurasi.TAG_LAT);
//                    String lng = jo.getString(konfigurasi.TAG_LNG);
//                    populateTenantList().add(new Tenant(id, product, tenant_name, price, rating, lat, lng));
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }


    }
}
