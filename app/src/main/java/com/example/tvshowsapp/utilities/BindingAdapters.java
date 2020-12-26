package com.example.tvshowsapp.utilities;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class BindingAdapters {

    @BindingAdapter("android:imageURL")
    public static void setImageURL(ImageView imageview,String URL){
        try{
            imageview.setAlpha(0f);
            Picasso.get().load(URL).noFade().into(imageview, new Callback() {
                @Override
                public void onSuccess() {
                    imageview.animate().setDuration(300).alpha(1f).start();
                }

                @Override
                public void onError(Exception e) {

                }
            });

        }catch (Exception ignored){

        }
    }
}
