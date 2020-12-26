package com.example.tvshowsapp.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.tvshowsapp.repositories.MostPopularTVShowsRepository;
import com.example.tvshowsapp.responses.TVShowResponse;

public class MostPpularTVShowsViewModel extends ViewModel {
    
    private MostPopularTVShowsRepository mostPopularTVShowsRepository;

    public MostPpularTVShowsViewModel() {
        mostPopularTVShowsRepository = new MostPopularTVShowsRepository();
    }

    public LiveData<TVShowResponse> getMostPopularTvShows(int page) {
        return mostPopularTVShowsRepository.getMostPopularTVShowa(page);
    }
}
