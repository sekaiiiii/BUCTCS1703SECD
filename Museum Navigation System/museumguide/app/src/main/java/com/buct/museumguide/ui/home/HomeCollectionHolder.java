package com.buct.museumguide.ui.home;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.buct.museumguide.R;

public class HomeCollectionHolder extends RecyclerView.ViewHolder {
    public Context context;
    public TextView collType;
    public ImageView collImg1;
    public TextView collName1;
    public ImageView collImg2;
    public TextView collName2;
    public TextView noColl;
    public LinearLayout coll1Wrap;
    public LinearLayout coll2Wrap;

    public HomeCollectionHolder(Context context, @NonNull View view) {
        super(view);
        this.collType = (TextView) view.findViewById(R.id.collType);
        Drawable drawableTop = context.getResources().getDrawable(R.drawable.ic_collection);
        this.collType.setCompoundDrawablesWithIntrinsicBounds(null, drawableTop, null,null);
        this.collImg1 = (ImageView) view.findViewById(R.id.collImg1);
        this.collName1 = (TextView) view.findViewById(R.id.collName1);
        this.collImg2 = (ImageView) view.findViewById(R.id.collImg2);
        this.collName2 = (TextView) view.findViewById(R.id.collName2);
        this.noColl = (TextView) view.findViewById(R.id.noColl);
        this.coll1Wrap = (LinearLayout) view.findViewById(R.id.coll1Wrap);
        this.coll2Wrap = (LinearLayout) view.findViewById(R.id.coll2Wrap);
    }
}
