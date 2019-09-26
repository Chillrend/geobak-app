package org.geobak.geobakapp;

import android.content.Intent;
import android.net.Uri;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import org.geobak.geobakapp.fragment.FragmentAccount;
import org.geobak.geobakapp.fragment.FragmentFavorite;
import org.geobak.geobakapp.fragment.FragmentHistory;
import org.geobak.geobakapp.fragment.FragmentHome;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener,
        FragmentAccount.OnFragmentInteractionListener,
        FragmentHome.OnFragmentInteractionListener,
        FragmentFavorite.OnFragmentInteractionListener,
        FragmentHistory.OnFragmentInteractionListener{

    private BottomNavigationView bm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadFragment(new FragmentHome());

        bm = findViewById(R.id.bottom_navigation_view);
        bm.setOnNavigationItemSelectedListener(this);

    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_content_layout, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()){
            case R.id.home_menu:
                fragment = new FragmentHome();
                break;
            case R.id.history_menu:
                fragment = new FragmentHistory();
                break;
            case R.id.favorite_menu:
                fragment = new FragmentFavorite();
                break;
            case R.id.account_menu:
                fragment = new FragmentAccount();
                break;
        }
        return loadFragment(fragment);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
