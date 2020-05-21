package com.buct.museumguide.ui.home;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.buct.museumguide.R;

public class HomeEducationHolder extends RecyclerView.ViewHolder {
    public Context context;
    public TextView eduType;
    public TextView eduTitle;
    public TextView eduContent;
    public ImageView eduImg;
    public TextView noEdu;

    public HomeEducationHolder(Context context, @NonNull View view) {
        super(view);
        this.eduType = (TextView) view.findViewById(R.id.eduType);
        Drawable drawableTop = context.getResources().getDrawable(R.drawable.ic_education_icon);
        this.eduType.setCompoundDrawablesWithIntrinsicBounds(null, drawableTop, null,null);
        this.eduTitle = (TextView) view.findViewById(R.id.eduTitle);
        this.eduContent = (TextView) view.findViewById(R.id.eduContent);
        this.eduImg = (ImageView) view.findViewById(R.id.eduImg);
        this.noEdu = (TextView) view.findViewById(R.id.noEdu);
    }
}
