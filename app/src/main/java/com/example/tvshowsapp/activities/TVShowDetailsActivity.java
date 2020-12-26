package com.example.tvshowsapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.text.HtmlCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.tvshowsapp.R;
import com.example.tvshowsapp.adapters.ImageSliderAdapter;
import com.example.tvshowsapp.databinding.ActivityTVShowDetailsBinding;
import com.example.tvshowsapp.models.TVShow;
import com.example.tvshowsapp.viewmodels.TVShowDetailsViewModel;

public class TVShowDetailsActivity extends AppCompatActivity {

    private ActivityTVShowDetailsBinding activityTVShowDetailsBinding;
    private TVShowDetailsViewModel tvShowDetailsViewModel;
    private TVShow tvShow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityTVShowDetailsBinding = DataBindingUtil.setContentView(this,R.layout.activity_t_v_show_details);
        doInitialization();
    }

    private void doInitialization() {
        tvShowDetailsViewModel = new ViewModelProvider(this).get(TVShowDetailsViewModel.class);
        activityTVShowDetailsBinding.imageBack.setOnClickListener(view -> onBackPressed());
        getTVShowDetails();
    }

    private void getTVShowDetails(){
        activityTVShowDetailsBinding.setIsLoading(true);
        String tvShowId = String.valueOf(getIntent().getIntExtra("id",-1));
        tvShowDetailsViewModel.getTVShowDetails(tvShowId).observe(
                this,tvShowDetailsResponses -> {
                    activityTVShowDetailsBinding.setIsLoading(false);
                    if (tvShowDetailsResponses.getTvShowDetails() !=null){
                        if (tvShowDetailsResponses.getTvShowDetails().getPictures() != null){
                            loadImagesSlider(tvShowDetailsResponses.getTvShowDetails().getPictures());
                        }
                        activityTVShowDetailsBinding.setTvShowImageURL(
                                tvShowDetailsResponses.getTvShowDetails().getImagePath()
                        );
                        activityTVShowDetailsBinding.imageTVShow.setVisibility(View.VISIBLE);
                        activityTVShowDetailsBinding.setDescription(
                                String.valueOf(
                                        HtmlCompat.fromHtml(
                                                tvShowDetailsResponses.getTvShowDetails().getDescription(),
                                                HtmlCompat.FROM_HTML_MODE_LEGACY
                                        )
                                )
                        );
                        activityTVShowDetailsBinding.textDescription.setVisibility(View.VISIBLE);
                        activityTVShowDetailsBinding.textReadMore.setVisibility(View.VISIBLE);
                        activityTVShowDetailsBinding.textReadMore.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (activityTVShowDetailsBinding.textReadMore.getText().toString().equals("read more")){
                                    activityTVShowDetailsBinding.textDescription.setMaxLines(Integer.MAX_VALUE);
                                    activityTVShowDetailsBinding.textDescription.setEllipsize(null);
                                    activityTVShowDetailsBinding.textReadMore.setText("read less");
                                } else {
                                    activityTVShowDetailsBinding.textDescription.setMaxLines(4);
                                    activityTVShowDetailsBinding.textDescription.setEllipsize(TextUtils.TruncateAt.END);
                                    activityTVShowDetailsBinding.textReadMore.setText("read more");
                                }
                            }
                        });
                        loadBasicTVShowDetails();
                    }
                }
        );
    }
    private void loadImagesSlider(String[] sliderImages){
        activityTVShowDetailsBinding.sliderViewPager.setOffscreenPageLimit(1);
        activityTVShowDetailsBinding.sliderViewPager.setAdapter(new ImageSliderAdapter(sliderImages));
        activityTVShowDetailsBinding.sliderViewPager.setVisibility(View.VISIBLE);
        activityTVShowDetailsBinding.viewFadingEdge.setVisibility(View.VISIBLE);
        setupSliderIndicators(sliderImages.length);
        activityTVShowDetailsBinding.sliderViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentSliderIndicator(position);
            }
        });
    }
    private void setupSliderIndicators(int count){
        ImageView[] indicators = new ImageView[count];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(8,0,8,0);
        for (int i=0; i< indicators.length; i++){
            indicators[i] = new ImageView(getApplicationContext());
            indicators[i].setImageDrawable(ContextCompat.getDrawable(
                    getApplicationContext(),
                    R.drawable.background_slider_inactive
            ));
            indicators[i].setLayoutParams(layoutParams);
            activityTVShowDetailsBinding.layoutSliderIndicator.addView(indicators[i]);
        }
        activityTVShowDetailsBinding.layoutSliderIndicator.setVisibility(View.VISIBLE);
        setCurrentSliderIndicator(0);
    }
    private void setCurrentSliderIndicator(int position){
        int childCount = activityTVShowDetailsBinding.layoutSliderIndicator.getChildCount();
        for (int i=0; i< childCount; i++){
            ImageView imageView = (ImageView) activityTVShowDetailsBinding.layoutSliderIndicator.getChildAt(i);
            if (i == position){
                imageView.setImageDrawable(
                        ContextCompat.getDrawable(getApplicationContext(),R.drawable.background_slier_indicator_active)
                );
            }else {
                imageView.setImageDrawable(
                        ContextCompat.getDrawable(getApplicationContext(),R.drawable.background_slider_inactive)
                );
            }
        }
    }
    private void loadBasicTVShowDetails() {
        activityTVShowDetailsBinding.setTvShowName(tvShow.getName());
        activityTVShowDetailsBinding.setNetworkCountry(
                tvShow.getNetwork() + "(" +
                        tvShow.getCountry() + ")"
        );
        activityTVShowDetailsBinding.setStatus(tvShow.getStatus());
        activityTVShowDetailsBinding.setStartedDate(tvShow.getStarDate());
        activityTVShowDetailsBinding.textName.setVisibility(View.VISIBLE);
        activityTVShowDetailsBinding.textNetworkCountry.setVisibility(View.VISIBLE);
        activityTVShowDetailsBinding.textStatus.setVisibility(View.VISIBLE);
        activityTVShowDetailsBinding.textStarted.setVisibility(View.VISIBLE);
    }
}