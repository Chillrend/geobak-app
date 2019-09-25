package org.geobak.geobakapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import org.geobak.geobakapp.R;
import org.geobak.geobakapp.model.Tenant;
import org.geobak.geobakapp.utils.CircleTransform;

import java.util.List;

public class TenantRecyclerViewAdapter extends RecyclerView.Adapter<TenantRecyclerViewAdapter.ViewHolder> {
    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView tenantImage;
        TextView tenantProduct;
        TextView sellerName;
        TextView price;
        TextView distance;
        RatingBar rating;

        public ViewHolder (View itemView){
            super(itemView);

            tenantImage = itemView.findViewById(R.id.tenant_image_rv);
            tenantProduct = itemView.findViewById(R.id.tenant_product_rv);
            sellerName = itemView.findViewById(R.id.seller_name_rv);
            price = itemView.findViewById(R.id.price_rv);
            distance = itemView.findViewById(R.id.distance_rv);
            rating = itemView.findViewById(R.id.tenant_rating_bar_rv);
        }
    }

    private List<Tenant> itemList;
    private Context ctx;

    public TenantRecyclerViewAdapter(List<Tenant> itemList, Context ctx){
        this.itemList = itemList;
        this.ctx = ctx;
    }

    @Override
    public int getItemCount(){
        return itemList.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.bottom_sheet_recycler_view_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final TenantRecyclerViewAdapter.ViewHolder viewHolder, int i){
        Tenant tenant = itemList.get(i);

        Picasso.get().load(tenant.getImg_url()).transform(new CircleTransform()).into(viewHolder.tenantImage);
        viewHolder.tenantProduct.setText(tenant.getProduct());
        viewHolder.sellerName.setText(tenant.getTenant_name());
        viewHolder.price.setText(tenant.getPrice());

        //USING GEDUNG TIK LOCATION TO CALCULATE DISTANCE, CHANGE THE 2ND AND 4TH PARAMETER TO USER LOCATION
        Double distance = getDistance(tenant.getLat(), -6.372550, tenant.getLng() , 106.823974, 10, 10);
        int roundedDistance = (int) Math.round(distance);
        viewHolder.distance.setText(String.valueOf(roundedDistance) + " Meters");

        viewHolder.rating.setRating(tenant.getRating());

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView rv){

    }

    public static double getDistance(double lat1, double lat2, double lon1,
                                  double lon2, double el1, double el2) {

        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        double height = el1 - el2;

        distance = Math.pow(distance, 2) + Math.pow(height, 2);

        return Math.sqrt(distance);
    }
}
