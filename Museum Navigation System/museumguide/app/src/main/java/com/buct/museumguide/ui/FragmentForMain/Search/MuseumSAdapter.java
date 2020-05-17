package com.buct.museumguide.ui.FragmentForMain.Search;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.buct.museumguide.R;
import com.buct.museumguide.bean.Museum;
import com.bumptech.glide.Glide;

import java.util.List;

public class MuseumSAdapter extends RecyclerView.Adapter< MuseumSAdapter.ViewHolder> {

    private List<Museum> mMuseumList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView open_time;
        TextView close_time;
        TextView longitude;
        TextView environment;
        TextView service;
        TextView exhibition;
        TextView latitude;
        ImageView image;

        public  ViewHolder (View view){
            super(view);
            name=view.findViewById(R.id.search_museum_name);
            open_time=view.findViewById(R.id.search_museum_open_time);
            close_time=view.findViewById(R.id.search_museum_close_time);
            longitude=view.findViewById(R.id.search_museum_longitude);
            latitude=view.findViewById(R.id.search_museum_latitude);
            environment=view.findViewById(R.id.search_museum_environment);
            service=view.findViewById(R.id.search_museum_service);
            exhibition=view.findViewById(R.id.search_museum_exhibition);
            image=view.findViewById(R.id.search_museum_iamge);
        }
    }



    public MuseumSAdapter(List<Museum> museumList){
        mMuseumList=museumList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.search_museum_item,parent,false);
        final ViewHolder holder=new ViewHolder(view);
        return  holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Museum exhibition=mMuseumList.get(position);
        holder.name.setText(exhibition.getName());
//        holder.open_time.setText("材质： "+exhibition.getOpen_time());
//        holder.close_time.setText("博物馆id： "+exhibition.getClose_time());
//        holder.latitude.setText("标签： "+exhibition.getLatitude());
//        holder.longitude.setText("内容： "+exhibition.getLongitude());
        holder.service.setText(" 服务： "+exhibition.getService_score());
        holder.environment.setText(" 环境： "+exhibition.getEnvironment_score());
        holder.exhibition.setText(" 展览： "+exhibition.getExhibition_score());
//        Uri uri = Uri.fromFile(new File(exhibition.getImgUrl()));
//        holder.image.setImageURI(uri);
        Glide.with(holder.itemView)
                .load("http://img.m.focus.cn/q_70/app/48/4878fa2bfd5a1a93186c7aefd37b01e4.jpg")
                .into(holder.image);

    }

    @Override
    public int getItemCount() {

        return mMuseumList.size();

    }
}
