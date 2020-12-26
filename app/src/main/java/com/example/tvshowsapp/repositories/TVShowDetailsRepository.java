package com.example.tvshowsapp.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.tvshowsapp.network.ApiClient;
import com.example.tvshowsapp.network.ApiService;
import com.example.tvshowsapp.responses.TVShowDetailsResponses;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TVShowDetailsRepository {

    private ApiService apiService;

    public TVShowDetailsRepository() {
        apiService = ApiClient.getRetrofit().create(ApiService.class);
    }

    public LiveData<TVShowDetailsResponses> getTVShowDetails(String tvShowId){
        MutableLiveData<TVShowDetailsResponses> data = new MutableLiveData<>();
        apiService.getTVShowDetail(tvShowId).enqueue(new Callback<TVShowDetailsResponses>() {
            @Override
            public void onResponse(Call<TVShowDetailsResponses> call, Response<TVShowDetailsResponses> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<TVShowDetailsResponses> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }
}
