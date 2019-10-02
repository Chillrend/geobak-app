package org.geobak.geobakapp.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonObject;
import org.geobak.geobakapp.MainActivity;
import org.geobak.geobakapp.R;
import org.geobak.geobakapp.utils.ApiCall;
import org.geobak.geobakapp.utils.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentRegister.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentRegister#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentRegister extends Fragment {

    public static String USER_SHARED_PREF = "USER_SHARED_PREF";

    private EditText register_email;
    private EditText register_full_name;
    private EditText register_phone_number;
    private EditText register_address;
    private EditText register_password;
    private EditText register_confirm_password;
    private Button register_button;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public static final String FRAGMENT_REGISTER_TAG = "FRAGMENT_REGISTER_TAG";

    private OnFragmentInteractionListener mListener;

    public FragmentRegister() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentRegister.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentRegister newInstance(String param1, String param2) {
        FragmentRegister fragment = new FragmentRegister();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fragment_register, container, false);



    }



    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        register_button = view.findViewById(R.id.register_button);

        register_email = view.findViewById(R.id.register_email);
        register_full_name = view.findViewById(R.id.register_full_name);
        register_phone_number = view.findViewById(R.id.register_phone_number);
        register_address = view.findViewById(R.id.register_address);
        register_password = view.findViewById(R.id.register_password);
        register_confirm_password = view.findViewById(R.id.register_confirm_password);

        register_button.setOnClickListener(this::onClick);

    }


    private boolean verifyPassword(){

        if(register_password.getText().toString().equals(register_confirm_password.getText().toString())){
            return true;
        } else{
            register_confirm_password.setError("Password Do Not Match");
            return false;
        }
    }

    public void onClick(View v) {
        if(v == register_button){
            //Validate form e.g register_full_name.getText().toString().trim().equals("")
            if(!verifyPassword()) return;
            ApiService service = ApiCall.getClient().create(ApiService.class);
            Call<JsonObject> call = service.goRegister(register_full_name.getText().toString(), register_email.getText().toString(), register_phone_number.getText().toString(), register_address.getText().toString(), register_password.getText().toString());

            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    JsonObject resp = response.body();
                    if(!resp.get("status").getAsString().equals("200")){
                        Toast.makeText(getContext(), "Register Failed!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    String email = resp.get("email").getAsString();

                    SharedPreferences.Editor edit = getActivity().getSharedPreferences(USER_SHARED_PREF, Context.MODE_PRIVATE).edit();
                    edit.putString("email", email);
                    edit.apply();

                    Toast.makeText(getContext(), "Register Success, Taking you back to Home screen..", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getActivity(), MainActivity.class);
                    getActivity().startActivity(i);
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    t.printStackTrace();
                    Toast.makeText(getContext(), "Register Failed!", Toast.LENGTH_SHORT).show();
                }
            });
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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    }
