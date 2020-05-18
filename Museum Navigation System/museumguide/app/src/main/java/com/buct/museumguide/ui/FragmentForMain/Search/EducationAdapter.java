package com.buct.museumguide.ui.FragmentForMain.Search;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.buct.museumguide.R;
import com.buct.museumguide.bean.Education;
import com.bumptech.glide.Glide;

import java.util.List;

public class EducationAdapter extends RecyclerView.Adapter<EducationAdapter.ViewHolder> {

    private List<Education> mEducationList;
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

    public EducationAdapter(List<Education> educationList) {
        mEducationList = educationList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.search_enducation_item,parent,false);
        final ViewHolder holder=new ViewHolder(view);
        return  holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Education exhibition=mEducationList.get(position);
        holder.name.setText(exhibition.getName());
        holder.start_time.setText(" 开始时间： "+exhibition.getStart_time());
        holder.end_time.setText(" 开始时间： "+exhibition.getEnd_time());
//        holder.time.setText("时间： "+exhibition.getTime());
//        holder.tag.setText("标签： "+exhibition.getTag());
        holder.content.setText(exhibition.getContent());
        holder.cooperator.setText(" 合作机构： "+exhibition.getCooperator());
        Glide.with(holder.itemView)
                .load(exhibition.getImgUrl())
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return mEducationList.size();
    }
}
