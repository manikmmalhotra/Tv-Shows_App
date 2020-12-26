package com.example.tvshowsapp.network;

import com.example.tvshowsapp.responses.TVShowDetailsResponses;
import com.example.tvshowsapp.responses.TVShowResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("most-popular")
    Call<TVShowResponse> getMostPopularTvShows(@Query("page") int page);

    @GET("show-details")
    Call<TVShowDetailsResponses> getTVShowDetail(@Query("q") String tvShowId);
}
