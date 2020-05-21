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
    private static final int TYPE_FOOTER = 2;

    // 上拉加载更多
    public static final int PULLUP_LOAD_MORE = 0;
    //正在加载中
    public static final int LOADING_MORE = 1;
    //没有加载更多 隐藏
    public static final int NO_LOAD_MORE = 2;

    //上拉加载更多状态-默认为0
    private int mLoadMoreStatus = 0;

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
        /*if(mHeaderView == null) return TYPE_NORMAL;
        if(position == 0) return TYPE_HEADER;
        return TYPE_NORMAL;*/
        if(mHeaderView == null) {
            if(position + 1 == getItemCount())
                return TYPE_FOOTER;
            return TYPE_NORMAL;
        }
        if(position == 0) return TYPE_HEADER;
        if(position + 1 == getItemCount())
            return TYPE_FOOTER;
        return TYPE_NORMAL;
    }

    public ArrayList<News> getData() {
        return news;
    }

    public void addDatas(ArrayList<News> datas) {
        news.addAll(datas);
        notifyDataSetChanged();
    }

    public void clearAndAddDatas(ArrayList<News> datas) {
        news.clear();
        news = datas;
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
        if(viewType == TYPE_NORMAL) {
            View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.newitem, parent, false);
            return new NewsHolder(layout);
        }
        else {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.footview, parent, false);
            return new FootHolder(v);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if(getItemViewType(position) == TYPE_HEADER) return;

        // 这里可能有问题
        if(viewHolder instanceof NewsHolder) {
            final int pos = getRealPosition(viewHolder);
            News museumNews = news.get(pos);
            final NewsHolder myHolder = (NewsHolder) viewHolder;
            myHolder.newsTitle.setText(museumNews.getTitle());
            myHolder.newsContent.setText(museumNews.getContent());
            myHolder.newsTime.setText(museumNews.getTime());
            myHolder.url = museumNews.getUrl();
//            Glide.with(myHolder.itemView)
//                    .load(museumNews.getImgUrl())
//                    .into(myHolder.newsImg);
            myHolder.cardView.setOnClickListener(v -> {
                if (onItemClickListener != null) {
                    int pos1 = getRealPosition(myHolder);
                    onItemClickListener.onItemClick(myHolder.cardView, pos1);
                }
            });

            myHolder.cardView.setOnLongClickListener(v -> {
                if (onItemClickListener != null) {
                    int pos12 = getRealPosition(myHolder);
                    onItemClickListener.onItemLongClick(myHolder.cardView, pos12);
                }
                //表示此事件已经消费，不会触发单击事件
                return true;
            });
        }
        else {
            ((FootHolder) viewHolder).mTip.setText("正在努力加载中...");
        }
    }

    private int getRealPosition(RecyclerView.ViewHolder holder) {
        int position = holder.getLayoutPosition();
        return mHeaderView == null ? position : position - 1;
    }

    @Override
    public int getItemCount() {
//        return mHeaderView == null ? news.size() : news.size() + 1;
        if(mHeaderView == null) {
            if(news != null) {
                return news.size() + 1;
            }
            else
                return 0;
        }
        else {
            if(news != null) {
                return news.size() + 2;
            }
            else
                return 0;
        }
    }

    class NewsHolder extends RecyclerView.ViewHolder{
        TextView newsContent;//概述
        TextView newsTime;//时间
        TextView newsTitle;//标题
        CardView cardView;
//        ImageView newsImg;
        String url;
        NewsHolder(@NonNull View itemView) {
            super(itemView);
            if(itemView == mHeaderView) return;
            newsTitle=itemView.findViewById(R.id.newsTitle);
            newsContent=itemView.findViewById(R.id.newsContent);
            newsTime=itemView.findViewById(R.id.newsTime);
            cardView=itemView.findViewById(R.id.card_view);
//            newsImg=itemView.findViewById(R.id.newsImg);
        }
    }
    class FootHolder extends RecyclerView.ViewHolder {
        public TextView mTip;
        public FootHolder(View itemView) {
            super(itemView);
            mTip = itemView.findViewById(R.id.tv_tip);
        }
    }

    /**
     * 更新加载更多状态
     *
     * @param status
     */
    public void changeMoreStatus(int status) {
        mLoadMoreStatus = status;
        notifyDataSetChanged();
    }
}
