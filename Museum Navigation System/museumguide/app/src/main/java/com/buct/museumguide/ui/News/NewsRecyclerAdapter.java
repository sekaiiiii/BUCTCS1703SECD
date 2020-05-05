package com.buct.museumguide.ui.News;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.buct.museumguide.R;
import com.buct.museumguide.bean.News;
import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class NewsRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<News> news = new ArrayList<>();
    private NewsRecyclerAdapter.OnItemClickListener onItemClickListener;

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_NORMAL = 1;
    private View mHeaderView;

    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        notifyItemInserted(0);
    }
    public View getHeaderView() {
        return mHeaderView;
    }
    @Override
    public int getItemViewType(int position) {
        if(mHeaderView == null) return TYPE_NORMAL;
        if(position == 0) return TYPE_HEADER;
        return TYPE_NORMAL;
    }

    public void addDatas(ArrayList<News> datas) {
        news.addAll(datas);
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }

    // ② 定义一个设置点击监听器的方法
    public void setOnItemClickListener(NewsRecyclerAdapter.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(mHeaderView != null && viewType == TYPE_HEADER) return new NewsHolder(mHeaderView);
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.newitem,parent,false);
        return new NewsHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NotNull RecyclerView.ViewHolder viewHolder, int position) {
        if(getItemViewType(position) == TYPE_HEADER) return;

        final int pos = getRealPosition(viewHolder);
        News museumNews=news.get(pos);
        final NewsHolder myHolder = (NewsHolder) viewHolder;
        myHolder.newsTitle.setText(museumNews.getTitle());
        myHolder.newsContent.setText(museumNews.getContent());
        myHolder.newsTime.setText(museumNews.getTime());
        myHolder.url=museumNews.getUrl();
        Glide.with(myHolder.itemView)
                .load(museumNews.getImgUrl())
                .into(myHolder.newsImg);
        myHolder.cardView.setOnClickListener(v -> {
            if(onItemClickListener != null) {
                int pos1 = getRealPosition(myHolder);
                onItemClickListener.onItemClick(myHolder.cardView, pos1);
            }
        });

        myHolder.cardView.setOnLongClickListener(v -> {
            if(onItemClickListener != null) {
                int pos12 = getRealPosition(myHolder);
                onItemClickListener.onItemLongClick(myHolder.cardView, pos12);
            }
            //表示此事件已经消费，不会触发单击事件
            return true;
        });
    }

    private int getRealPosition(RecyclerView.ViewHolder holder) {
        int position = holder.getLayoutPosition();
        return mHeaderView == null ? position : position - 1;
    }

    @Override
    public int getItemCount() {
        return mHeaderView == null ? news.size() : news.size() + 1;
    }

    class NewsHolder extends RecyclerView.ViewHolder{
        TextView newsContent;//概述
        TextView newsTime;//时间
        TextView newsTitle;//标题
        CardView cardView;
        ImageView newsImg;
        String url;
        NewsHolder(@NonNull View itemView) {
            super(itemView);
            if(itemView == mHeaderView) return;
            newsTitle=itemView.findViewById(R.id.newsTitle);
            newsContent=itemView.findViewById(R.id.newsContent);
            newsTime=itemView.findViewById(R.id.newsTime);
            cardView=itemView.findViewById(R.id.card_view);
            newsImg=itemView.findViewById(R.id.newsImg);
        }
    }
}
