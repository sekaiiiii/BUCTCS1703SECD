package com.buct.museumguide.ui.FragmentForMain.Search;

import android.text.TextPaint;
import android.util.Log;
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
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
//            open_time=view.findViewById(R.id.search_museum_open_time);
//            close_time=view.findViewById(R.id.search_museum_close_time);
//            longitude=view.findViewById(R.id.search_museum_longitude);
//            latitude=view.findViewById(R.id.search_museum_latitude);
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
        TextPaint tv=holder.name.getPaint();tv.setFakeBoldText(true);
//        holder.open_time.setText("材质： "+exhibition.getOpen_time());
//        holder.close_time.setText("博物馆id： "+exhibition.getClose_time());
//        holder.latitude.setText("标签： "+exhibition.getLatitude());
//        holder.longitude.setText("内容： "+exhibition.getLongitude());
        if(exhibition.getService_score().equals("null")||exhibition.getService_score().equals("")){
            holder.service.setText(" 服务： "+"3.0");
        }else{
            if(exhibition.getService_score().length()>3){
                holder.service.setText(" 服务： "+exhibition.getService_score().substring(0,3));
            }
            else holder.service.setText(" 服务： "+exhibition.getService_score());
        }

        if(exhibition.getEnvironment_score().equals("null")||exhibition.getService_score().equals("")){
            holder.environment.setText(" 环境： "+"3.0");
        }
        else{
            if(exhibition.getEnvironment_score().length()>3){
                holder.environment.setText(" 环境： "+exhibition.getEnvironment_score().substring(0,3));
            }
            else holder.environment.setText(" 环境： "+exhibition.getEnvironment_score());
        }

        if(exhibition.getExhibition_score().equals("null")||exhibition.getExhibition_score().equals("")){
            holder.exhibition.setText(" 展览： "+"3.0");
        }
        else{
            if(exhibition.getExhibition_score().length()>3){
                holder.exhibition.setText(" 展览： "+exhibition.getExhibition_score().substring(0,3));
            }
            else holder.exhibition.setText(" 展览： "+exhibition.getExhibition_score());
        }
//        Uri uri = Uri.fromFile(new File(exhibition.getImgUrl()));
//        holder.image.setImageURI(uri);
        String imageUrl="";

        try{
            JSONArray imageArray=exhibition.getImage_list();
            Log.d("geturl",imageArray.toString());
            String imageShow;
            imageShow=imageArray.getString(0);

            Log.d("geturl","imageShow");
            imageUrl="http://192.144.239.176:8080/"+imageShow.toString();
        }
        catch (JSONException e){
            e.printStackTrace();
            Log.d("geturl",e.toString());
        }
        Log.d("geturl",imageUrl);
        if(!imageUrl.equals("")){
            Glide.with(holder.itemView)
                    .load(imageUrl)
                    .into(holder.image);
        }

    }

    @Override
    public int getItemCount() {

        return mMuseumList.size();

    }
}
