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
import com.buct.museumguide.bean.Collection;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class CollRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<Collection> colls = new ArrayList<>();
    private CollRecyclerAdapter.OnItemClickListener onItemClickListener;

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

    public void addDatas(ArrayList<Collection> datas) {
        colls.addAll(datas);
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }

    // ② 定义一个设置点击监听器的方法
    public void setOnItemClickListener(CollRecyclerAdapter.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(mHeaderView != null && viewType == TYPE_HEADER) return new CollHolder(mHeaderView);
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.coll_item, parent,false);
        return new CollHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NotNull RecyclerView.ViewHolder viewHolder, int position) {
        if(getItemViewType(position) == TYPE_HEADER) return;

        final int pos = getRealPosition(viewHolder);
        Collection coll = colls.get(pos);
        final CollHolder myHolder = (CollHolder) viewHolder;
        myHolder.collName.setText(coll.getName());
        try {
            Glide.with(myHolder.itemView)
                    .load(getImageUrl(coll.getImage_list()))
                    .apply(new RequestOptions().error(R.drawable.emptyimage2))
                    .into(myHolder.collImg);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        myHolder.collCardView.setOnClickListener(v -> {
            if(onItemClickListener != null) {
                int pos1 = getRealPosition(myHolder);
                onItemClickListener.onItemClick(myHolder.collCardView, pos1);
            }
        });

        myHolder.collCardView.setOnLongClickListener(v -> {
            if(onItemClickListener != null) {
                int pos12 = getRealPosition(myHolder);
                onItemClickListener.onItemLongClick(myHolder.collCardView, pos12);
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
        return mHeaderView == null ? colls.size() : colls.size() + 1;
    }

    class CollHolder extends RecyclerView.ViewHolder{
        ImageView collImg;
        TextView collName;
        CardView collCardView;
        CollHolder(@NonNull View itemView) {
            super(itemView);
            if(itemView == mHeaderView) return;
            collImg=itemView.findViewById(R.id.collImg);
            collName=itemView.findViewById(R.id.collName);
            collCardView=itemView.findViewById(R.id.collCardView);
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
