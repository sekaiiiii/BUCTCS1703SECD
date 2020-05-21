package com.buct.museumguide.ui.FragmentForMain.CommonList;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.buct.museumguide.R;
import com.buct.museumguide.bean.Exhibition;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class ExhiRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<Exhibition> exhis = new ArrayList<>();
    private ExhiRecyclerAdapter.OnItemClickListener onItemClickListener;

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

    public void addDatas(ArrayList<Exhibition> datas) {
        exhis.addAll(datas);
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }

    // ② 定义一个设置点击监听器的方法
    public void setOnItemClickListener(ExhiRecyclerAdapter.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(mHeaderView != null && viewType == TYPE_HEADER) return new ExhiHolder(mHeaderView);
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.exhi_item, parent,false);
        return new ExhiHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NotNull RecyclerView.ViewHolder viewHolder, int position) {
        if(getItemViewType(position) == TYPE_HEADER) return;

        final int pos = getRealPosition(viewHolder);
        Exhibition exhi = exhis.get(pos);
        final ExhiHolder myHolder = (ExhiHolder) viewHolder;
        myHolder.exhiTitle.setText(exhi.getName());
        myHolder.exhiTime.setText(exhi.getTime());
        myHolder.exhiMuseumName.setText(exhi.getName());
        try {
            Glide.with(myHolder.itemView)
                    .load(getImageUrl(exhi.getImage_list()))
                    .apply(new RequestOptions().error(R.drawable.emptyimage2))
                    .into(myHolder.exhiImg);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        myHolder.exhiCardView.setOnClickListener(v -> {
            if(onItemClickListener != null) {
                int pos1 = getRealPosition(myHolder);
                onItemClickListener.onItemClick(myHolder.exhiCardView, pos1);
            }
        });

        myHolder.exhiCardView.setOnLongClickListener(v -> {
            if(onItemClickListener != null) {
                int pos12 = getRealPosition(myHolder);
                onItemClickListener.onItemLongClick(myHolder.exhiCardView, pos12);
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
        return mHeaderView == null ? exhis.size() : exhis.size() + 1;
    }

    class ExhiHolder extends RecyclerView.ViewHolder{
        TextView exhiTitle;
        TextView exhiTime;
        TextView exhiMuseumName;
        ImageView exhiImg;
        CardView exhiCardView;
        ExhiHolder(@NonNull View itemView) {
            super(itemView);
            if(itemView == mHeaderView) return;
            exhiTitle=itemView.findViewById(R.id.exhiTitle);
            exhiTime=itemView.findViewById(R.id.exhiTime);
            exhiMuseumName=itemView.findViewById(R.id.exhiMuseumName);
            exhiImg=itemView.findViewById(R.id.exhiImg);
            exhiCardView=itemView.findViewById(R.id.exhiCardView);
        }
    }
    public String getImageUrl(JSONArray imgList) throws JSONException {
        String imgurl = "";
        if(imgList.length()==0){
            imgurl = "";
        }
        else {
            imgurl = "http://192.144.239.176:8080/" + imgList.get(0).toString();
        }
        return imgurl;
    }
}
