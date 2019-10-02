package org.geobak.geobakapp.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.transition.Fade;
import androidx.transition.TransitionInflater;
import androidx.transition.TransitionSet;

import com.google.gson.JsonObject;
import org.geobak.geobakapp.MainActivity;
import org.geobak.geobakapp.R;
import org.geobak.geobakapp.SessionManager;
import org.geobak.geobakapp.utils.ApiCall;
import org.geobak.geobakapp.utils.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentLogin.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentLogin#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentLogin extends Fragment {

    public static final int CONNECTION_TIMEOUT=10000;
    public static final int READ_TIMEOUT=15000;
    SessionManager sessionManager;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final long MOVE_DEFAULT_TIME = 1000;
    private static final long FADE_DEFAULT_TIME = 300;

    public static String LOGIN_FRAGMENT_TAG = "LOGIN_FRAGMENT_TAG";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private EditText email;
    private EditText password;

    public Button getRegister_btn() {
        return register_btn;
    }

    private Button register_btn;

    private EditText register_address;
    private EditText register_email;
    private EditText register_full_name;
    private EditText register_phone_number;
    private EditText register_password;
    private EditText register_confirm_password;

    private Button login_button;


    private ImageView logo_image;

    private FragmentManager mFragmentManager;

    private OnFragmentInteractionListener mListener;

    public FragmentLogin() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentLogin.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentLogin newInstance(String param1, String param2) {
        FragmentLogin fragment = new FragmentLogin();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        sessionManager = new SessionManager(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment_login, container, false);
        register_btn = view.findViewById(R.id.register_button);
        logo_image = view.findViewById(R.id.logo_image);
        email = view.findViewById(R.id.email);
        password = view.findViewById(R.id.password);
        login_button = view.findViewById(R.id.login_button);

        return view;

    }

    private void performTransition(){
        if(getActivity().isDestroyed()){
            return;
        }

        Fragment previousFragment = mFragmentManager.findFragmentById(R.id.frame_layout);
        FragmentRegister fr = new FragmentRegister();

        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();

        // 1. Exit for Previous Fragment
        Fade exitFade = new Fade();
        exitFade.setDuration(FADE_DEFAULT_TIME);
        previousFragment.setExitTransition(exitFade);

        // 2. Shared Elements Transition
        TransitionSet enterTransitionSet = new TransitionSet();
        enterTransitionSet.addTransition(TransitionInflater.from(getActivity()).inflateTransition(android.R.transition.move));
        enterTransitionSet.setDuration(MOVE_DEFAULT_TIME);
        enterTransitionSet.setStartDelay(FADE_DEFAULT_TIME);
        ((Fragment) fr).setSharedElementEnterTransition(enterTransitionSet);

        // 3. Enter Transition for New Fragment
        Fade enterFade = new Fade();
        enterFade.setStartDelay(MOVE_DEFAULT_TIME + FADE_DEFAULT_TIME);
        enterFade.setDuration(FADE_DEFAULT_TIME);
        ((Fragment) fr).setEnterTransition(enterFade);

        View logo = logo_image;
        fragmentTransaction.addSharedElement(logo, logo.getTransitionName());
        fragmentTransaction.replace(R.id.frame_layout, (Fragment) fr);
        fragmentTransaction.commitAllowingStateLoss();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){

        mFragmentManager = getActivity().getSupportFragmentManager();
        sessionManager = new SessionManager(getContext());

        login_button = view.findViewById(R.id.login_button);

        email = view.findViewById(R.id.email);
        password = view.findViewById(R.id.password);

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Check for empty form
                if(email.getText().toString().trim().equals("") || password.getText().toString().trim().equals("")){
                    Toast.makeText(getContext(), "Both field are required..", Toast.LENGTH_SHORT).show();
                    return;
                }

                ApiService service = ApiCall.getClient().create(ApiService.class);
                Call<JsonObject> call = service.goLogin(email.getText().toString().trim(), password.getText().toString());
                call.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        JsonObject resp = response.body();
                        if(!resp.get("status").getAsString().equals("200")){
                            Toast.makeText(getContext(), "Email and password unknown", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        String email = resp.get("email").getAsString();

                        SharedPreferences.Editor edit = getActivity().getSharedPreferences(FragmentRegister.USER_SHARED_PREF, Context.MODE_PRIVATE).edit();
                        edit.putString("email", email);
                        edit.apply();

                        Toast.makeText(getContext(), "Login Success, Taking you back to Home screen..", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getActivity(), MainActivity.class);
                        getActivity().startActivity(i);
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {

                    }
                });
            }
        });

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performTransition();
            }
        });
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
