package com.buct.museumguide.ui.home;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.buct.museumguide.R;

public class HomeExhibitionHolder extends RecyclerView.ViewHolder {
    public Context context;
    public TextView exhiType;
    public TextView exhiTitle;
    public TextView exhiContent;
    public ImageView exhiImg;
    public TextView noExhi;

    public HomeExhibitionHolder(Context context, @NonNull View view) {
        super(view);
        this.exhiType = (TextView) view.findViewById(R.id.exhiType);
        Drawable drawableTop = context.getResources().getDrawable(R.drawable.ic_exhibition);
        this.exhiType.setCompoundDrawablesWithIntrinsicBounds(null, drawableTop, null,null);
        this.exhiTitle = (TextView) view.findViewById(R.id.exhiTitle);
        this.exhiContent = (TextView) view.findViewById(R.id.exhiContent);
        this.exhiImg = (ImageView) view.findViewById(R.id.exhiImg);
        this.noExhi = (TextView) view.findViewById(R.id.noExhi);
    }
}