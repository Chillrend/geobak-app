package org.geobak.geobakapp.model;

import java.util.ArrayList;
import java.util.List;

public class TxHistory {
    private String img_url;
    private String product_name;
    private String tenant_name;
    private String tx_date;
    private int amount_price;

    public TxHistory(String img_url, String product_name, String tenant_name, String tx_date, int amount_price) {
        this.img_url = img_url;
        this.product_name = product_name;
        this.tenant_name = tenant_name;
        this.tx_date = tx_date;
        this.amount_price = amount_price;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getTenant_name() {
        return tenant_name;
    }

    public void setTenant_name(String tenant_name) {
        this.tenant_name = tenant_name;
    }

    public String getTx_date() {
        return tx_date;
    }

    public void setTx_date(String tx_date) {
        this.tx_date = tx_date;
    }

    public int getAmount_price() {
        return amount_price;
    }

    public void setAmount_price(int amount_price) {
        this.amount_price = amount_price;
    }

    public static List<TxHistory> populateTxHistory(){
        List<TxHistory> txHistories = new ArrayList<>();
        txHistories.add(new TxHistory("https://picsum.photos/50/50", "Nasi Goreng Anter Lama", "Kantek", "29 August 2019 19:40", 20000));
        txHistories.add(new TxHistory("https://picsum.photos/50/50", "Sate Babiq", "Pak Joshua", "29 August 2019 19:40", 12000));
        txHistories.add(new TxHistory("https://picsum.photos/50/50", "Japanese Food", "Izakaya", "29 August 2019 19:40", 15000));
        txHistories.add(new TxHistory("https://picsum.photos/50/50", "Baso Mantab", "Pak Kumis", "29 August 2019 19:40", 10000));
        txHistories.add(new TxHistory("https://picsum.photos/50/50", "Nasi Uduk", "Bu Nasduk", "29 August 2019 19:40", 5000));
        return txHistories;
    }
}
