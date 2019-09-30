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
import org.geobak.geobakapp.model.Result;
import org.geobak.geobakapp.model.Tenant;
import org.geobak.geobakapp.model.TxHistory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class FavRecyclerViewAdapter extends RecyclerView.Adapter<FavRecyclerViewAdapter.ViewHolder> {
    public static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView fav_tenant_image;
        TextView fav_tenant_product;
        TextView fav_tenant_name;
        TextView fav_amount_price;
        TextView fav_rating;

        public ViewHolder(View itemView){
            super(itemView);

            fav_tenant_image = itemView.findViewById(R.id.tenant_image_fav_rv);
            fav_tenant_product = itemView.findViewById(R.id.tenant_product_fav_rv);
            fav_tenant_name = itemView.findViewById(R.id.seller_name_fav_rv);
            fav_amount_price = itemView.findViewById(R.id.price_fav_rv);
            fav_rating = itemView.findViewById(R.id.tenant_rating_fav_rv);
        }
    }

    private List<Result> itemList;
    private Context ctx;

    public FavRecyclerViewAdapter(List<Result> itemList, Context ctx) {
        this.itemList = itemList;
        this.ctx = ctx;
    }

    @Override
    public FavRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.favorite_recycler_view_item, viewGroup, false);
        return new FavRecyclerViewAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final FavRecyclerViewAdapter.ViewHolder viewHolder, int i){
        Result item = itemList.get(i);

        Picasso.get().load("https://picsum.photos/50/50").into(viewHolder.fav_tenant_image);
        viewHolder.fav_tenant_product.setText(item.getNameProduct());
        viewHolder.fav_tenant_name.setText(item.getNameSeller());
        viewHolder.fav_amount_price.setText(item.getPriceUnit());
        viewHolder.fav_rating.setText("5.0");
    }

    @Override
    public int getItemCount(){
        return itemList.size();
    }
}
