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
import com.buct.museumguide.bean.Education;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class EduRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<Education> edus = new ArrayList<>();
    private EduRecyclerAdapter.OnItemClickListener onItemClickListener;

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

    public void addDatas(ArrayList<Education> datas) {
        edus.addAll(datas);
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }

    // ② 定义一个设置点击监听器的方法
    public void setOnItemClickListener(EduRecyclerAdapter.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(mHeaderView != null && viewType == TYPE_HEADER) return new EduHolder(mHeaderView);
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.edu_item, parent,false);
        return new EduHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NotNull RecyclerView.ViewHolder viewHolder, int position) {
        if(getItemViewType(position) == TYPE_HEADER) return;

        final int pos = getRealPosition(viewHolder);
        Education edu = edus.get(pos);
        final EduHolder myHolder = (EduHolder) viewHolder;
        myHolder.eduTitle.setText(edu.getName());
        myHolder.eduContent.setText(edu.getContent());
        myHolder.eduTime.setText(edu.getTime());
        try {
            Glide.with(myHolder.itemView)
                    .load(getImageUrl(edu.getImage_list()))
                    .apply(new RequestOptions().error(R.drawable.emptyimage2))
                    .into(myHolder.eduImg);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        myHolder.eduCardView.setOnClickListener(v -> {
            if(onItemClickListener != null) {
                int pos1 = getRealPosition(myHolder);
                onItemClickListener.onItemClick(myHolder.eduCardView, pos1);
            }
        });

        myHolder.eduCardView.setOnLongClickListener(v -> {
            if(onItemClickListener != null) {
                int pos12 = getRealPosition(myHolder);
                onItemClickListener.onItemLongClick(myHolder.eduCardView, pos12);
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
        return mHeaderView == null ? edus.size() : edus.size() + 1;
    }

    class EduHolder extends RecyclerView.ViewHolder{
        TextView eduTitle;
        TextView eduContent;
        TextView eduTime;
        ImageView eduImg;
        CardView eduCardView;
        EduHolder(@NonNull View itemView) {
            super(itemView);
            if(itemView == mHeaderView) return;
            eduTitle=itemView.findViewById(R.id.eduTitle);
            eduContent=itemView.findViewById(R.id.eduContent);
            eduTime=itemView.findViewById(R.id.eduTime);
            eduImg=itemView.findViewById(R.id.eduImg);
            eduCardView=itemView.findViewById(R.id.eduCardView);
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
