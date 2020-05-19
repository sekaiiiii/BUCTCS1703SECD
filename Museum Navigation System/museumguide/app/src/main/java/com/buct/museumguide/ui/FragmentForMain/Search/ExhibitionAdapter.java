package com.buct.museumguide.ui.FragmentForMain.Search;

import android.content.SharedPreferences;
import android.net.Uri;
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
import com.buct.museumguide.bean.Exhibition;
import com.buct.museumguide.ui.FragmentForMain.Comment.CommentAdapter;
import com.buct.museumguide.ui.FragmentForMain.Comment.PerComment;
import com.bumptech.glide.Glide;

import java.io.File;
import java.util.List;

public class ExhibitionAdapter extends RecyclerView.Adapter<ExhibitionAdapter.ViewHolder> {

    private List<Exhibition> mExhibitionList;


    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView content;
        TextView start_time;
        TextView end_time;
        TextView time;
        TextView tag;
        ImageView image;

        public  ViewHolder (View view){
            super(view);
            name=view.findViewById(R.id.search_exhibition_name);
            content=view.findViewById(R.id.search_exhibition_content);
            start_time=view.findViewById(R.id.search_exhibition_start_time);
            end_time=view.findViewById(R.id.search_exhibition_end_time);
            time=view.findViewById(R.id.search_exhibition_time);
            tag=view.findViewById(R.id.search_exhibition_tag);
            image=view.findViewById(R.id.search_exhibition_iamge);
        }
    }



    public ExhibitionAdapter(List<Exhibition> exhibitionList){
        mExhibitionList=exhibitionList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.search_exhibition_item,parent,false);
        final ViewHolder holder=new ViewHolder(view);
        return  holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Exhibition exhibition=mExhibitionList.get(position);
        holder.name.setText(exhibition.getName());
        holder.start_time.setText("开始时间： "+exhibition.getStart_time());
        holder.end_time.setText("开始时间： "+exhibition.getEnd_time());
//        holder.time.setText("时间： "+exhibition.getTime());
//        holder.tag.setText("标签： "+exhibition.getTag());
//        holder.content.setText("内容： "+exhibition.getContent());
//        Uri uri = Uri.fromFile(new File(exhibition.getImgUrl()));
//        holder.image.setImageURI(uri);
        Glide.with(holder.itemView)
                .load(exhibition.getImgUrl())
                .into(holder.image);

    }

    @Override
    public int getItemCount() {

        return mExhibitionList.size();

    }
}


