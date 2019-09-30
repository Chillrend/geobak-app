package org.geobak.geobakapp.utils;

import org.geobak.geobakapp.model.favorite.Favorite;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {

    @GET("readproduct.php")
    Call<Favorite> showFavorite();

}
