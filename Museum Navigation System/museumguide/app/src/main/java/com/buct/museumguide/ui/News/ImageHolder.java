package com.buct.museumguide.ui.News;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.buct.museumguide.R;

public class ImageHolder extends RecyclerView.ViewHolder {
    public ImageView bannerNewsImage;
    public TextView bannerNewsTitle;
    public TextView bannerNewsTime;

    public ImageHolder(@NonNull View view) {
        super(view);
        this.bannerNewsImage = (ImageView) view.findViewById(R.id.bannerNewsImage);
        this.bannerNewsTitle = (TextView) view.findViewById(R.id.bannerNewsTitle);
        this.bannerNewsTime = (TextView) view.findViewById(R.id.bannerNewsTime);
    }
}
