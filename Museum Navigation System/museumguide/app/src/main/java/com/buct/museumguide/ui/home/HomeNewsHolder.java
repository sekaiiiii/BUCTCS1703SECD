package com.buct.museumguide.ui.home;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.buct.museumguide.R;

public class HomeNewsHolder extends RecyclerView.ViewHolder {
    public Context context;
    public TextView newsType;
    public TextView newsTitle;
    public TextView newsContent;
    public TextView newsTime;
    public TextView noNews;

    public HomeNewsHolder(Context context, @NonNull View view) {
        super(view);
        this.newsType = (TextView) view.findViewById(R.id.newsType);
        Drawable drawableTop = context.getResources().getDrawable(R.drawable.ic_homenews);
        this.newsType.setCompoundDrawablesWithIntrinsicBounds(null, drawableTop, null,null);
        this.newsTitle = (TextView) view.findViewById(R.id.homeNewsTitle);
        this.newsContent = (TextView) view.findViewById(R.id.homeNewsContent);
        this.newsTime = (TextView) view.findViewById(R.id.homeNewsTime);
        this.noNews = (TextView) view.findViewById(R.id.noNews);
    }
}