package org.geobak.geobakapp;

import android.net.Uri;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.transition.Fade;
import androidx.transition.TransitionInflater;
import androidx.transition.TransitionSet;
import org.geobak.geobakapp.fragment.FragmentLogin;
import org.geobak.geobakapp.fragment.FragmentRegister;

public class ActivityLogin extends AppCompatActivity implements FragmentLogin.OnFragmentInteractionListener, FragmentRegister.OnFragmentInteractionListener {


    private Button btn_login;
    private LinearLayout logo_wrapper;
    private FrameLayout frameLayout;
    private FragmentManager mFragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mFragmentManager = getSupportFragmentManager();

        FragmentLogin f = new FragmentLogin();

        loadInitialFragment(f);


    }


    private void loadInitialFragment(Fragment fr){
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fr, FragmentLogin.LOGIN_FRAGMENT_TAG);
        fragmentTransaction.commit();
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
