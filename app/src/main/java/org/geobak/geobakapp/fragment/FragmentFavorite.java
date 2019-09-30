package org.geobak.geobakapp.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import org.geobak.geobakapp.model.favorite.Favorite;
import org.geobak.geobakapp.model.favorite.Result;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.airbnb.lottie.L;
import org.geobak.geobakapp.R;
import org.geobak.geobakapp.adapter.FavRecyclerViewAdapter;
import org.geobak.geobakapp.model.Tenant;
import org.geobak.geobakapp.utils.ApiCall;
import org.geobak.geobakapp.utils.ApiService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentFavorite.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentFavorite#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentFavorite extends Fragment {

    private String JSON_STRING;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView rv;

    private OnFragmentInteractionListener mListener;

    public FragmentFavorite() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentFavorite.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentFavorite newInstance(String param1, String param2) {
        FragmentFavorite fragment = new FragmentFavorite();
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
        return inflater.inflate(R.layout.fragment_fragment_favorite, container, false);


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedinstancestate){
        rv = view.findViewById(R.id.favorite_rv);
        ApiService apiService = ApiCall.getClient().create(ApiService.class);
        Call<Favorite> call = apiService.showFavorite();
        call.enqueue(new Callback<Favorite>() {
            @Override
            public void onResponse(Call<Favorite> call, Response<Favorite> response) {
                try {
                    Favorite favorite = response.body();
                    List<Result> result = favorite.getResult();
                    FavRecyclerViewAdapter favRecyclerViewAdapter = new FavRecyclerViewAdapter(result, getContext());

                    LinearLayoutManager llm = new LinearLayoutManager(getContext());
                    llm.setOrientation(RecyclerView.VERTICAL);
                    DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rv.getContext(), llm.getOrientation());
                    rv.setLayoutManager(new LinearLayoutManager(getContext()));
                    rv.addItemDecoration(dividerItemDecoration);
                    rv.setAdapter(favRecyclerViewAdapter);

                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(getContext(), getString(R.string.error_cant_get_data),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Favorite> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getContext(),getString(R.string.error_cant_get_data),Toast.LENGTH_SHORT).show();
            }
        });
    }

    // TODO: Rename method, update argument and hook method into UI event
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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
