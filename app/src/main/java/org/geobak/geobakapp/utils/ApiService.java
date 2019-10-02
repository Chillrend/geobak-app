package org.geobak.geobakapp.utils;

import com.google.gson.JsonObject;
import org.geobak.geobakapp.model.favorite.Favorite;

import org.geobak.geobakapp.model.history.History;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {

    @GET("readproduct.php")
    Call<Favorite> showFavorite();

    @POST("register.php")
    @FormUrlEncoded
    Call<JsonObject> goRegister(@Field("name")String name, @Field("email")String email, @Field("number") String number, @Field("address") String address, @Field("password") String password);

    @POST("login.php")
    @FormUrlEncoded
    Call<JsonObject> goLogin(@Field("email")String email, @Field("password")String password);

    @POST("historyTransactions.php")
    @FormUrlEncoded
    Call<History> getHistory(@Field("email") String email);
}
