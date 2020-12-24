package com.example.tvshowsapp.network;

import com.example.tvshowsapp.responses.TVShowResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("most_popular")
    Call<TVShowResponse> getMostPopularTvShows(@Query("page") int page);
}
