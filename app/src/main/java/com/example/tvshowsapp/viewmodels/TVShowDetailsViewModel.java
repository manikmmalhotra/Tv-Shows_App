package com.example.tvshowsapp.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.tvshowsapp.repositories.TVShowDetailsRepository;
import com.example.tvshowsapp.responses.TVShowDetailsResponses;

public class TVShowDetailsViewModel extends ViewModel {
    private TVShowDetailsRepository tvShowDetailsRepository;

    public TVShowDetailsViewModel(){
        tvShowDetailsRepository = new TVShowDetailsRepository();
    }

    public LiveData<TVShowDetailsResponses> getTVShowDetails(String tvShowId){
        return tvShowDetailsRepository.getTVShowDetails(tvShowId);
    }
}
