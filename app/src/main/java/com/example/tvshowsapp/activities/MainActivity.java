package com.example.tvshowsapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.tvshowsapp.R;
import com.example.tvshowsapp.adapters.TVShowsAdapter;
import com.example.tvshowsapp.databinding.ActivityMainBinding;
import com.example.tvshowsapp.listeners.TVShowsListener;
import com.example.tvshowsapp.models.TVShow;
import com.example.tvshowsapp.repositories.MostPopularTVShowsRepository;
import com.example.tvshowsapp.viewmodels.MostPpularTVShowsViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TVShowsListener {
    private ActivityMainBinding activityMainBinding;
    private List<TVShow> tvShows = new ArrayList<>();
    private TVShowsAdapter  tvShowsAdapter;
    private int currentPage= 1;
    private int totalAvailblePages = 1;

    private MostPpularTVShowsViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        doInitialization();
    }

    private void doInitialization(){
        activityMainBinding.tvshowsrecylerview.setHasFixedSize(true);
        viewModel = new ViewModelProvider(this).get(MostPpularTVShowsViewModel.class);
        tvShowsAdapter = new TVShowsAdapter(tvShows,this);
        activityMainBinding.tvshowsrecylerview.setAdapter(tvShowsAdapter);
        activityMainBinding.tvshowsrecylerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!activityMainBinding.tvshowsrecylerview.canScrollVertically(1)){
                    if(currentPage <= totalAvailblePages){
                        currentPage++;
                        getMostPopularTVShows();
                    }
                }
            }
        });
        getMostPopularTVShows();
    }

    private void getMostPopularTVShows() {
        toggleLoading();
        viewModel.getMostPopularTvShows(currentPage).observe(this,mostPopularTVShows -> {
           toggleLoading();
           if (mostPopularTVShows !=null){
               totalAvailblePages = mostPopularTVShows.getPages();
               if (mostPopularTVShows.getTvShows() != null){
                   int oldCount = tvShows.size();
                   tvShows.addAll(mostPopularTVShows.getTvShows());
                   tvShowsAdapter.notifyItemRangeInserted(oldCount,tvShows.size());
               }
           }

        });
    }
    private void toggleLoading(){
        if (currentPage ==1){
            if (activityMainBinding.getIsLoading() != null && activityMainBinding.getIsLoading()){
                activityMainBinding.setIsLoading(false);
            }else {
                activityMainBinding.setIsLoading(true);
            }
        }else {
            if (activityMainBinding.getIsLoadingMore() != null && activityMainBinding.getIsLoadingMore()){
                activityMainBinding.setIsLoadingMore(false);
            }else {
                activityMainBinding.setIsLoadingMore(true);
            }
        }
    }

    @Override
    public void onTVShowClicked(TVShow tvShow) {
        Intent intent = new Intent(getApplicationContext(),TVShowDetailsActivity.class);
        intent.putExtra("tvShow",tvShow);
        startActivity(intent);
    }
}