package com.buct.museumguide.ui.News;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.buct.museumguide.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    private ArrayList<MuseumNews>news;
    private NewsAdapter.OnItemClickListener onItemClickListener;
    static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView textView1;//概述
        private TextView textView2;//作者
        //private TextView textView3;
        private TextView textView4;//时间
        private TextView textView5;//标题
        private String url;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView1=itemView.findViewById(R.id.textView5);
            textView2=itemView.findViewById(R.id.textView6);
            //textView3=itemView.findViewById(R.id.textView7);
            textView4=itemView.findViewById(R.id.textView8);
            textView5=itemView.findViewById(R.id.textView9);
        }
    }
    public NewsAdapter(ArrayList<MuseumNews> newsitem){
        news=newsitem;
    }
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }

    // ② 定义一个设置点击监听器的方法
    public void setOnItemClickListener(NewsAdapter.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.newitem,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        MuseumNews museumNews=news.get(position);
        holder.textView5.setText(museumNews.getTitle());
        holder.textView2.setText(museumNews.getAuthor());
        holder.textView4.setText(museumNews.getTime());
        holder.textView1.setText(museumNews.getContent());
        holder.url=museumNews.getUrl();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if(onItemClickListener != null) {
                    int pos = holder.getLayoutPosition();
                    onItemClickListener.onItemClick(holder.itemView, pos);
                }
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(onItemClickListener != null) {
                    int pos = holder.getLayoutPosition();
                    onItemClickListener.onItemLongClick(holder.itemView, pos);
                }
                //表示此事件已经消费，不会触发单击事件
                return true;
            }
        });
    }


    @Override
    public int getItemCount() {
        return news.size();
    }
}
