package com.example.tugasrssrecyclerview;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class PicassoClient {

    public static void downloadImage(Context c, String imageUrl, ImageView img){
        if (imageUrl!=null && imageUrl.length()>0) {
            Picasso.get().load(imageUrl).placeholder(R.drawable.ic_launcher_background).into(img);
        }
    }
}
