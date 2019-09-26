package org.geobak.geobakapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import org.geobak.geobakapp.R;
import org.geobak.geobakapp.model.TxHistory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TxHistoryRecyclerViewAdapter extends RecyclerView.Adapter<TxHistoryRecyclerViewAdapter.ViewHolder> {
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

    private List<TxHistory> itemList;
    private Context ctx;

    public TxHistoryRecyclerViewAdapter(List<TxHistory> itemList, Context ctx) {
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
        TxHistory item = itemList.get(i);

        Picasso.get().load(item.getImg_url()).into(viewHolder.tx_tenant_image);
        viewHolder.tx_tenant_product.setText(item.getProduct_name());
        viewHolder.tx_tenant_name.setText(item.getTenant_name());
        viewHolder.tx_amount_price.setText(String.valueOf(item.getAmount_price()));

        //TODO: CHANGE THE DATE FORMAT ACCORDING TO RETURNED DATA FROM API
        DateFormat df = new SimpleDateFormat("dd MMMM yyyy hh:mm");

        DateFormat dfdate = new SimpleDateFormat("dd MMMM yyyy");
        DateFormat dftime = new SimpleDateFormat("hh:mm");
        Date date = new Date();
        try {
            date = df.parse(item.getTx_date());
        }catch (ParseException pe){
            pe.printStackTrace();
        }

        viewHolder.tx_date.setText(dfdate.format(date));
        viewHolder.tx_time.setText(dftime.format(date));
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView rv){

    }

}
