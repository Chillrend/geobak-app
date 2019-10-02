package org.geobak.geobakapp;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.FragmentActivity;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.kennyc.view.MultiStateView;
import org.geobak.geobakapp.adapter.TenantRecyclerViewAdapter;
import org.geobak.geobakapp.model.Tenant;
import org.geobak.geobakapp.model.favorite.Favorite;
import org.geobak.geobakapp.model.favorite.Result;
import org.geobak.geobakapp.utils.ApiCall;
import org.geobak.geobakapp.utils.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

public class MapsMainActivity extends AppCompatActivity implements OnMapReadyCallback, LocationListener, TenantRecyclerViewAdapter.OnTenantClickListener {

    private GoogleMap mMap;

    static final int TWO_MINUTES = 1000 * 60 * 2;

    private MultiStateView map_location_detail;
    private LinearLayout bottom_sheet_peeker, bottom_sheet;
    private BottomSheetBehavior bottomSheetBehavior;
    private MultiStateView msv;
    private RecyclerView tenant_rv;
    private Button searchBtn;
    private Context ctx;
    private Location currentBestLocation = null;
    private LocationManager mLocationManager;
    private List<Result> results;
    private TenantRecyclerViewAdapter tenantRecyclerViewAdapter;

    private ImageView tenantImage;
    private TextView itemName, tenantRating, sellerName, distance, price, shippingCosts, totalPrice;

    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute

    //TODO: IMPORTANT!!! GET YOUR OWN GOOGLE_MAPS_API.XML, I'VE IGNORED THE XML FOR THIS PROJECT
    //USE THIS LINK TO GET API https://console.developers.google.com/flows/enableapi?apiid=maps_android_backend&keyType=CLIENT_SIDE_ANDROID&r=6C:D1:65:8B:0B:91:F9:15:2F:7D:55:5A:DA:00:92:59:1A:F8:78:CA%3Borg.geobak.geobakapp
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_main);
        ctx = this;

        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if(!mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) buildNoGpsAlertMessage();

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        map_location_detail = findViewById(R.id.map_place_detail);
        bottom_sheet_peeker = findViewById(R.id.peeker_wrapper);
        bottom_sheet = findViewById(R.id.bottom_sheet);
        bottomSheetBehavior = BottomSheetBehavior.from(bottom_sheet);
        tenant_rv = findViewById(R.id.tenant_rv);

        tenantImage = findViewById(R.id.tenant_image);
        itemName = findViewById(R.id.item_name);
        tenantRating = findViewById(R.id.tenant_rating);
        sellerName = findViewById(R.id.seller_name);
        distance = findViewById(R.id.distance);
        price = findViewById(R.id.price);
        shippingCosts = findViewById(R.id.shipping_cost);
        totalPrice = findViewById(R.id.total_price);


        map_location_detail.setViewState(MultiStateView.ViewState.EMPTY);

        int height = (int) getResources().getDimension(R.dimen.peeker_size) + (int) getResources().getDimension(R.dimen.small_spacing);

        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) map_location_detail.getLayoutParams();
        params.setMargins((int) getResources().getDimension(R.dimen.small_spacing),0,(int) getResources().getDimension(R.dimen.small_spacing), height);
        map_location_detail.setLayoutParams(params);

    }

    private Location getLastBestLocation(){
        Location locationGps = null;
        Location locationNetwork = null;
        if(checkLocationPermission()){
            locationGps = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            locationNetwork = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }
        long gpsLocationTime = 0;

        if(null != locationGps){
            gpsLocationTime = locationGps.getTime();
        }

        long networkLocationTime = 0;
        if(null != locationNetwork){
            networkLocationTime = locationNetwork.getTime();
        }

        if ( 0 < gpsLocationTime - networkLocationTime ) {
            return locationGps;
        }
        else {
            return locationNetwork;
        }
    }

    private boolean isSameProvider(String provider1, String provider2) {
        if (provider1 == null) {
            return provider2 == null;
        }
        return provider1.equals(provider2);
    }

    protected boolean isBetterLocation(Location location, Location currentBestLocation) {
        if (currentBestLocation == null) {
            // A new location is always better than no location
            return true;
        }

        // Check whether the new location fix is newer or older
        long timeDelta = location.getTime() - currentBestLocation.getTime();
        boolean isSignificantlyNewer = timeDelta > TWO_MINUTES;
        boolean isSignificantlyOlder = timeDelta < -TWO_MINUTES;
        boolean isNewer = timeDelta > 0;

        // If it's been more than two minutes since the current location, use the new location,
        // because the user has likely moved.
        if (isSignificantlyNewer) {
            return true;
            // If the new location is more than two minutes older, it must be worse.
        } else if (isSignificantlyOlder) {
            return false;
        }

        // Check whether the new location fix is more or less accurate
        int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation.getAccuracy());
        boolean isLessAccurate = accuracyDelta > 0;
        boolean isMoreAccurate = accuracyDelta < 0;
        boolean isSignificantlyLessAccurate = accuracyDelta > 200;

        // Check if the old and new location are from the same provider
        boolean isFromSameProvider = isSameProvider(location.getProvider(),
                currentBestLocation.getProvider());

        // Determine location quality using a combination of timeliness and accuracy
        if (isMoreAccurate) {
            return true;
        } else if (isNewer && !isLessAccurate) {
            return true;
        } else return isNewer && !isSignificantlyLessAccurate && isFromSameProvider;
    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle(getString(R.string.dialog_ask_loc_permission_title))
                        .setMessage(getString(R.string.dialog_ask_loc_permission_description))
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(MapsMainActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {


                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                    }

                } else {
                    finish();
                }
                return;
            }

        }
    }

    @Override
    public void onLocationChanged(Location location) {
        makeUseOfNewLocation(location);
        if(currentBestLocation == null) currentBestLocation = location;

        Toast.makeText(this, "Location Updated", Toast.LENGTH_LONG).show();

        LatLng myLocation = new LatLng(location.getLatitude(), location.getLongitude());

        mMap.clear();
        mMap.addMarker(new MarkerOptions().position(myLocation).title("My location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 18));
        //Emulating API Call

        ApiService service = ApiCall.getClient().create(ApiService.class);
        Call<Favorite> call = service.showFavorite();
        call.enqueue(new Callback<Favorite>() {
            @Override
            public void onResponse(Call<Favorite> call, Response<Favorite> response) {
                try {
                    Favorite f = response.body();
                    results = f.getResult();

                    results = TenantRecyclerViewAdapter.trimListBasedOnLocation(results, location);

                    tenantRecyclerViewAdapter = new TenantRecyclerViewAdapter(results, ctx, MapsMainActivity.this::onTenantClick, location);

                    LinearLayoutManager lm = new LinearLayoutManager(ctx);

                    tenant_rv.setLayoutManager(lm);
                    tenant_rv.setAdapter(tenantRecyclerViewAdapter);

                    for (int i = 0; i < results.size(); i++) {
                        Result tenant = results.get(i);

                        LatLng tenantMarker = new LatLng(Double.parseDouble(tenant.getLatitude()), Double.parseDouble(tenant.getLongitude()));

                        MarkerOptions markerOptions = new MarkerOptions().position(tenantMarker).title(tenant.getNameProduct());
                        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_tenant_marker));

                        mMap.addMarker(markerOptions);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(MapsMainActivity.this, "Can't get data..", Toast.LENGTH_SHORT);
                }
            }

            @Override
            public void onFailure(Call<Favorite> call, Throwable t) {
                Toast.makeText(MapsMainActivity.this, "Can't get data..", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    private void makeUseOfNewLocation(Location location){
        if(isBetterLocation(location, currentBestLocation)){
            currentBestLocation = location;
        }
    }

    private void buildNoGpsAlertMessage(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.gps_disabled_ask))
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in kantek and move the camera
        LatLng kantek = new LatLng(-6.372342, 106.824228);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(kantek, 18));

        if(checkLocationPermission()){
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
        }
        // Process continues at onLocationChanged
    }

    @Override
    public void onTenantClick(int pos) {
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        Result item = results.get(pos);

        itemName.setText(item.getNameProduct());
        sellerName.setText(item.getNameSeller());
        price.setText("Rp. " + item.getPriceUnit());
        tenantRating.setText(item.getRating());

        Location myLocation = tenantRecyclerViewAdapter.getMyLocation();

        Double dist = TenantRecyclerViewAdapter.getDistance(Double.parseDouble(item.getLatitude()), myLocation.getLatitude(), Double.parseDouble(item.getLongitude()), myLocation.getLongitude(), 10,10);
        int rounded_dist = (int) Math.round(dist);

        distance.setText(String.valueOf(rounded_dist) + "m");
        shippingCosts.setText("Rp. 1000");

        int total = Integer.parseInt(item.getPriceUnit()) + 1000;

        totalPrice.setText("Rp. " + String.valueOf(total));

        map_location_detail.setViewState(MultiStateView.ViewState.CONTENT);
    }
}
