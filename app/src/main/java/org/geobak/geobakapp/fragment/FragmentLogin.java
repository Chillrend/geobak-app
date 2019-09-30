package org.geobak.geobakapp.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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

import org.geobak.geobakapp.MainActivity;
import org.geobak.geobakapp.R;
import org.geobak.geobakapp.SessionManager;

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

//
//
        register_email = view.findViewById(R.id.register_email);
        register_full_name = view.findViewById(R.id.register_full_name);
        register_phone_number = view.findViewById(R.id.register_phone_number);
        register_address = view.findViewById(R.id.register_address);
        register_password = view.findViewById(R.id.register_password);
        register_confirm_password = view.findViewById(R.id.register_confirm_password);

        return view;


    }

    public void checkLogin(String email, String password) {

        // Initialize  AsyncLogin() class with email and password
        new AsyncLogin().execute(email,password);
        sessionManager.createSession(email);


    }

    private class AsyncLogin extends AsyncTask<String, String, String>
    {
        ProgressDialog pdLoading = new ProgressDialog(getContext());
        HttpURLConnection conn;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //this method will be running on UI thread
            pdLoading.setMessage("\tLoading...");
            pdLoading.setCancelable(false);
            pdLoading.show();

        }
        @Override
        protected String doInBackground(String... params) {
            try {

                // Enter URL address where your php file resides
                url = new URL("http://192.168.43.47:81/geobak/login.php");

            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return "exception";
            }
            try {
                // Setup HttpURLConnection class to send and receive data from php and mysql
                conn = (HttpURLConnection)url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("POST");

                // setDoInput and setDoOutput method depict handling of both send and receive
                conn.setDoInput(true);
                conn.setDoOutput(true);



                // Append parameters to URL
                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("username", params[0])
                        .appendQueryParameter("password", params[1]);

                String query = builder.build().getEncodedQuery();

                // Open connection for sending data
                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();
                conn.connect();

            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                return "exception";
            }

            try {

                int response_code = conn.getResponseCode();

                // Check if successful connection made
                if (response_code == HttpURLConnection.HTTP_OK) {

                    // Read data sent from server
                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    // Pass data to onPostExecute method
                    return(result.toString());


                }else{

                    return("unsuccessful");
                }

            } catch (IOException e) {
                e.printStackTrace();
                return "exception";
            } finally {
                conn.disconnect();
            }


        }

        @Override
        protected void onPostExecute(String result) {

            //this method will be running on UI thread

            pdLoading.dismiss();

            if(result.equalsIgnoreCase("true"))
            {
                /* Here launching another activity when login successful. If you persist login state
                use sharedPreferences of Android. and logout button to clear sharedPreferences.
                 */
                sessionManager.createSession(email.getText().toString());


                Intent i = new Intent(getActivity(), MainActivity.class);
                startActivity(i);
                getActivity().finish();

            }else if (result.equalsIgnoreCase("false")){

                // If username and password does not match display a error message
//                Toast.makeText(getActivity().this, "Invalid email or password", Toast.LENGTH_LONG).show();
                Toast.makeText(getActivity(),"Invalid email or password",Toast.LENGTH_LONG).show();

            } else if (result.equalsIgnoreCase("exception") || result.equalsIgnoreCase("unsuccessful")) {

//                Toast.makeText(FragmentLogin.this, "oof something went wrong. Connection Problem.", Toast.LENGTH_LONG).show();
                Toast.makeText(getActivity(),"oof something went wrong. Connection Problem.",Toast.LENGTH_LONG).show();
            }
        }


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
                checkLogin(email.getText().toString(),password.getText().toString());
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

    private void addStory() {

        final String mail = register_email.getText().toString().trim();
        final String name = register_full_name.getText().toString().trim();
        final String phone = register_phone_number.getText().toString().trim();
        final String adrs = register_address.getText().toString().trim();
        final String pswrd = register_password.getText().toString().trim();
        final String cpswrd = register_confirm_password.getText().toString().trim();


        class Addstory extends AsyncTask<Void, Void, String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                Verify();


//                loading = ProgressDialog.show(getActivity().this, "Menambahkan...", "Tunggu...", false, false);
                loading = ProgressDialog.show(getActivity(), "Loading...", "Please wait...", true);

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
                Verify();
            }



            @Override
            protected String doInBackground(Void... v) {
                HashMap<String, String> params = new HashMap<>();
                params.put(konfigurasi.KEY_EMP_EMAIL, mail);
                params.put(konfigurasi.KEY_EMP_NAME, name);
                params.put(konfigurasi.KEY_EMP_PHONE, phone);
                params.put(konfigurasi.KEY_EMP_ADDRESS, adrs);
                params.put(konfigurasi.KEY_EMP_PASSWORD, pswrd);
//                params.put(konfigurasi.KEY_EMP_CPASSWORD, cpswrd);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(konfigurasi.URL_ADD_USER, params);
                return res;
            }
        }

        Addstory as = new Addstory();
        as.execute();
    }

    private boolean Verify(){

        if(register_password.getText().toString().equals(register_confirm_password.getText().toString())){
            return true;
        } else{
            register_confirm_password.setError("Password Do Not Match");
            return false;
        }
    }

    public void onClick(View v) {
        if(v == register_btn){
            addStory();

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
