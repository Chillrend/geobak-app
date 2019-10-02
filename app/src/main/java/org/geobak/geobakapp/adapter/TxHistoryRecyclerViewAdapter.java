package org.geobak.geobakapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import org.geobak.geobakapp.R;
import org.geobak.geobakapp.model.Tenant;
import org.geobak.geobakapp.model.TxHistory;
import org.geobak.geobakapp.model.history.Hasil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TxHistoryRecyclerViewAdapter extends RecyclerView.Adapter<TxHistoryRecyclerViewAdapter.ViewHolder> {
    private String JSON_STRING;
    public static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView tx_tenant_image;
        TextView tx_tenant_product;
        TextView tx_tenant_name;
        TextView tx_amount_price;
        TextView tx_date;
        TextView tx_time;


        public ViewHolder(View itemView){
            super(itemView);

            tx_tenant_image = itemView.findViewById(R.id.tenant_image_history_rv);
            tx_tenant_product = itemView.findViewById(R.id.tenant_product_history_rv);
            tx_tenant_name = itemView.findViewById(R.id.seller_name_history_rv);
            tx_amount_price = itemView.findViewById(R.id.price_history_rv);
            tx_date = itemView.findViewById(R.id.date_history_tx);
            tx_time = itemView.findViewById(R.id.time_history_tx);
        }
    }

    private List<Hasil> itemList;
    private Context ctx;

    public TxHistoryRecyclerViewAdapter(List<Hasil> itemList, Context ctx) {
        this.itemList = itemList;
        this.ctx = ctx;
    }

    @Override
    public int getItemCount(){
        return itemList.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.history_rv_item, viewGroup, false);
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(final TxHistoryRecyclerViewAdapter.ViewHolder viewHolder, int i){
        Hasil item = itemList.get(i);

        if(item.getImage().trim().equals("")) {
            Picasso.get().load("https://picsum.photos/50/50").into(viewHolder.tx_tenant_image);
        }else{
            Picasso.get().load(item.getImage()).into(viewHolder.tx_tenant_image);
        }

        viewHolder.tx_tenant_product.setText(item.getNameProduct());
        viewHolder.tx_tenant_name.setText(item.getNameSeller());
        viewHolder.tx_amount_price.setText(String.valueOf(item.getPrice()));


        viewHolder.tx_date.setText(item.getDateTransaction());
        viewHolder.tx_time.setText(item.getTimeTransaction());
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView rv){

    }

//    private void showEmployee(){
//        JSONObject jsonObject = null;
//        final ArrayList<Tenant> list = new ArrayList<>();
//
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
//                    list.add(new TxHistoryRecyclerViewAdapter(id, product, tenant_name, price, rating, lat, lng));
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            MyCustomListAdapter adapter = new MyCustomListAdapter(storyList.this, list);
//            listView1.setAdapter(adapter);
//            listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    String storyID = list.get(position).getId();
//                    Intent obj = new Intent(storyList.this, EditStory.class);
//                    obj.putExtra(konfigurasi.EMP_AUTH_STORY,storyID);
//                    startActivity(obj);
//                }
//            });
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }

}
