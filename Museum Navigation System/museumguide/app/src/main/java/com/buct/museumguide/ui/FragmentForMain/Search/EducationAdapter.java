package com.buct.museumguide.ui.FragmentForMain.Search;

import android.content.Context;
import android.content.Intent;
import android.text.TextPaint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.buct.museumguide.R;
import com.buct.museumguide.bean.Education;
import com.buct.museumguide.ui.ClassForNews.WebViewer;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

public class EducationAdapter extends RecyclerView.Adapter<EducationAdapter.ViewHolder> {

    private List<Education> mEducationList;
    private Context mContext;
    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView content;
        TextView start_time;
        TextView end_time;
        TextView time;
        TextView tag;
        ImageView image;
        TextView cooperator;
        public  ViewHolder (View view){
            super(view);
            name=view.findViewById(R.id.search_education_name);
            content=view.findViewById(R.id.search_education_content);
            start_time=view.findViewById(R.id.search_education_start_time);
            end_time=view.findViewById(R.id.search_education_end_time);
            time=view.findViewById(R.id.search_education_time);
            tag=view.findViewById(R.id.search_education_tag);
            cooperator=view.findViewById(R.id.search_education_cooperator);
            image=view.findViewById(R.id.search_education_iamge);
        }

    }

    public EducationAdapter(List<Education> educationList,Context context) {

        mEducationList = educationList;
        mContext=context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.search_enducation_item,parent,false);
        final ViewHolder holder=new ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position=holder.getAdapterPosition();//5.
                String url=mEducationList.get(position).getUrl();
                Intent intent=new Intent(mContext, WebViewer.class);
                intent.putExtra("uri",url);
                mContext.startActivity(intent);
                Toast.makeText(mContext, "正在为您跳转", Toast.LENGTH_SHORT).show();
            }
        });
        return  holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Education exhibition=mEducationList.get(position);
        holder.name.setText(exhibition.getName());
        TextPaint tp=holder.name.getPaint();tp.setFakeBoldText(true);
        if(exhibition.getStart_time().equals("null")){
            holder.start_time.setText(" 开始时间： 暂无");
        }
        else{
            holder.start_time.setText(" 开始时间： "+exhibition.getStart_time());
        }

        if(exhibition.getEnd_time().equals("null")){
            holder.end_time.setText(" 开始时间： 暂无");
        }else{
            holder.end_time.setText(" 开始时间： "+exhibition.getEnd_time());
        }
//        holder.time.setText("时间： "+exhibition.getTime());
//        holder.tag.setText("标签： "+exhibition.getTag());
        holder.content.setText(exhibition.getContent());
        if(exhibition.getCooperator().equals("null")){
            holder.cooperator.setText(" 合作机构： 暂无");
        }
        else holder.cooperator.setText(" 合作机构： "+exhibition.getCooperator());
//        Glide.with(holder.itemView)
//                .load(exhibition.getImgUrl())
//                .into(holder.image);

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
        return mEducationList.size();
    }
}
