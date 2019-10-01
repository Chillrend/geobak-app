package org.geobak.geobakapp.adapter;

import android.content.Context;
import android.location.Location;
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
import org.geobak.geobakapp.model.favorite.Result;
import org.geobak.geobakapp.utils.CircleTransform;

import java.util.ArrayList;
import java.util.List;

public class TenantRecyclerViewAdapter extends RecyclerView.Adapter<TenantRecyclerViewAdapter.ViewHolder> {
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView tenantImage;
        TextView tenantProduct;
        TextView sellerName;
        TextView price;
        TextView distance;
        RatingBar rating;
        OnTenantClickListener onTenantClickListener;

        public ViewHolder (View itemView, OnTenantClickListener onTenantClickListener){
            super(itemView);

            tenantImage = itemView.findViewById(R.id.tenant_image_rv);
            tenantProduct = itemView.findViewById(R.id.tenant_product_rv);
            sellerName = itemView.findViewById(R.id.seller_name_rv);
            price = itemView.findViewById(R.id.price_rv);
            distance = itemView.findViewById(R.id.distance_rv);
            rating = itemView.findViewById(R.id.tenant_rating_bar_rv);
            this.onTenantClickListener = onTenantClickListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onTenantClickListener.onTenantClick(getAdapterPosition());
        }
    }

    private List<Result> itemList = new ArrayList<>();
    private Context ctx;
    private OnTenantClickListener onTenantClickListener;
    private Location myLocation;

    public Location getMyLocation() {
        return myLocation;
    }

    public TenantRecyclerViewAdapter(List<Result> itemList, Context ctx, OnTenantClickListener onTenantClickListener, Location myLocation){
        for (Result item : itemList) {
            double distance = getDistance(Double.parseDouble(item.getLatitude()), myLocation.getLatitude(), Double.parseDouble(item.getLongitude()), myLocation.getLongitude(), 10, 10);
            int rounded_dist = (int) Math.round(distance);
            if (rounded_dist < 1000) {
                this.itemList.add(item);
            }
        }
        this.ctx = ctx;
        this.onTenantClickListener = onTenantClickListener;
        this.myLocation = myLocation;
    }

    public static List<Result> trimListBasedOnLocation(List<Result> itemList, Location location){
        List<Result> returnedItem = new ArrayList<>();
        for (Result item : itemList) {
            double distance = getDistance(Double.parseDouble(item.getLatitude()), location.getLatitude(), Double.parseDouble(item.getLongitude()), location.getLongitude(), 10, 10);
            int rounded_dist = (int) Math.round(distance);
            if (rounded_dist < 1000) {
                returnedItem.add(item);
            }
        }

        return  returnedItem;
    }

    @Override
    public int getItemCount(){
        return itemList.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.bottom_sheet_recycler_view_item, viewGroup, false);
        return new ViewHolder(v, onTenantClickListener);
    }

    @Override
    public void onBindViewHolder(final TenantRecyclerViewAdapter.ViewHolder viewHolder, int i){
        Result item = itemList.get(i);

        if(item.getImage() != null && !item.getImage().trim().equals("")){
            Picasso.get().load(item.getImage()).transform(new CircleTransform()).into(viewHolder.tenantImage);
        }else{
            Picasso.get().load("http://picsum.photos/50/50").transform(new CircleTransform()).into(viewHolder.tenantImage);
        }

        viewHolder.tenantProduct.setText(item.getNameProduct());
        viewHolder.sellerName.setText(item.getNameSeller());
        viewHolder.price.setText(item.getPriceUnit());

        //USING GEDUNG TIK LOCATION TO CALCULATE DISTANCE, CHANGE THE 2ND AND 4TH PARAMETER TO USER LOCATION
        Double myLat = myLocation.getLatitude();
        Double myLon = myLocation.getLongitude();
        Double distance = getDistance(Double.parseDouble(item.getLatitude()), myLat, Double.parseDouble(item.getLongitude()) , myLon, 10, 10);
        int roundedDistance = (int) Math.round(distance);
        viewHolder.distance.setText(String.valueOf(roundedDistance) + " Meters");

        Float ratingDouble = Float.parseFloat(item.getRating());

        viewHolder.rating.setRating(ratingDouble);

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

    public interface OnTenantClickListener{
        void onTenantClick(int pos);
    }
}
