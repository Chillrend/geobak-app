package org.geobak.geobakapp;

import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsMainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private RelativeLayout map_location_detail;
    private LinearLayout bottom_sheet_peeker;
    private Button searchBtn;

    //TODO: IMPORTANT!!! GET YOUR OWN GOOGLE_MAPS_API.XML, I'VE IGNORED THE XML FOR THIS PROJECT
    //USE THIS LINK TO GET API https://console.developers.google.com/flows/enableapi?apiid=maps_android_backend&keyType=CLIENT_SIDE_ANDROID&r=6C:D1:65:8B:0B:91:F9:15:2F:7D:55:5A:DA:00:92:59:1A:F8:78:CA%3Borg.geobak.geobakapp
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_main);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        map_location_detail = findViewById(R.id.map_place_detail);
        bottom_sheet_peeker = findViewById(R.id.peeker_wrapper);

        int height = (int) getResources().getDimension(R.dimen.peeker_size) + (int) getResources().getDimension(R.dimen.small_spacing);

        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) map_location_detail.getLayoutParams();
        params.setMargins((int) getResources().getDimension(R.dimen.small_spacing),0,(int) getResources().getDimension(R.dimen.small_spacing), height);
        map_location_detail.setLayoutParams(params);

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in kantek and move the camera
        LatLng kantek = new LatLng(-6.372342, 106.824228);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(kantek, 18));
    }
}