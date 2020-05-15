package com.buct.museumguide.ui.FragmentForMain.Comment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.buct.museumguide.R;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

    private List<PerComment> mCommentList;
    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView textViewName;
        TextView textViewMail;
        TextView textViewExhibition;
        TextView textViewTime;
        TextView textViewContent;
        TextView textViewService;
        TextView textViewEnvironment;
        View commentView;
        public ViewHolder(View view){
            super(view);
            commentView=view;
            textViewName=view.findViewById(R.id.comment_name);
            textViewMail=view.findViewById(R.id.comment_mail);
            textViewExhibition=view.findViewById(R.id.comment_exhibiton);
            textViewEnvironment=view.findViewById(R.id.comment_environment);
            textViewTime=view.findViewById(R.id.comment_time);
            textViewContent=view.findViewById(R.id.comment_content);
            textViewService=view.findViewById(R.id.comment_service);
        }
    }

    public CommentAdapter(List<PerComment>CommentList){
        mCommentList=CommentList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item,parent,false);
        final ViewHolder holder=new ViewHolder(view);
        holder.textViewName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),"you click it ",Toast.LENGTH_SHORT).show();
            }
        });
        return  holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PerComment perComment=mCommentList.get(position);
        holder.textViewContent.setText(perComment.getContent());
        holder.textViewName.setText(perComment.getName());
        holder.textViewMail.setText("   mail: "+perComment.getMail_address());
        holder.textViewExhibition.setText("展览: "+perComment.getExhibition_score());
        holder.textViewService.setText("服务: "+perComment.getService_score());
        holder.textViewTime.setText(perComment.getTime());
        holder.textViewEnvironment.setText("环境: "+perComment.getEnvironment_score());
    }

    @Override
    public int getItemCount() {
        return mCommentList.size();
    }
}
