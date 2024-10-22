package com.buct.museumguide.ui.FragmentForMain.MuseumList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.buct.museumguide.R;
import com.bumptech.glide.Glide;

import java.util.List;

public class MuseumTimeAdapter extends RecyclerView.Adapter<MuseumTimeAdapter.ViewHolder> {
    private Context context;
    private List<Museum> mMuseumList;
    private MuseumTimeAdapter.OnItemClickListener onItemClickListener;
    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView museumImage;
        TextView museumName;
        TextView museumLevel;
        TextView museumNumber;
        CardView cardView;
        public ViewHolder(View view){
            super(view);
            museumImage = view.findViewById(R.id.museum_image);
            museumName = view.findViewById(R.id.museum_name);
            museumLevel = view.findViewById(R.id.museum_level);
            museumNumber = view.findViewById(R.id.museum_exhibition_num);
            cardView = view.findViewById(R.id.museumList_card_view);
            //museumTest = view.findViewById(R.id.museum_test);
        }
    }


    public MuseumTimeAdapter(List<Museum> museumList){

        mMuseumList = museumList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.museum_time_items,parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Museum museum = mMuseumList.get(position);
        holder.museumName.setText(museum.getName());
        Glide.with(holder.itemView)
                .load(museum.getImgUrl())
                .into(holder.museumImage);
        holder.museumLevel.setText(museum.getLevel());
        holder.museumNumber.setText(museum.getExhibition_num());
        holder.cardView.setOnClickListener(view -> {
            if(onItemClickListener != null){
                int pos = getRealPosition(holder);
                onItemClickListener.onItemClick(holder.cardView,pos);
            }
        });

        holder.cardView.setOnLongClickListener(view -> {
            if(onItemClickListener != null){
                int pos = getRealPosition(holder);
                onItemClickListener.onItemLongClick(holder.cardView,pos);
            }
            return true;
        });
    }
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }
    public void setOnItemClickListener(MuseumTimeAdapter.OnItemClickListener listener){
        this.onItemClickListener = listener;
    }
    @Override
    public int getItemCount() {
        return mMuseumList.size();
    }


    private int getRealPosition(RecyclerView.ViewHolder holder){
        int position = holder.getLayoutPosition();
        return position;
    }

    public String getTitle(int position){
        Museum museum = mMuseumList.get(position);
        return museum.getName();
    }

    public void updata(List<Museum> list){
        this.mMuseumList = list;
        notifyDataSetChanged();
    }

    //根据recylerview 当前位置获取分类的首字母的char ascii码
    public int getSectionForPosition(int position) {
        return mMuseumList.get(position).getLetters().charAt(0);
    }

    public int getPositionForSection(int section) {
        for(int i=0; i<getItemCount(); i++){
            String str = mMuseumList.get(i).getLetters();
            char firstChar = str.toUpperCase().charAt(0);
            if(firstChar == section) {
                return i;
            }
        }
        return -1;
    }
    public int getID(int position) {
        Museum museum = mMuseumList.get(position);
        return museum.getId();
    }
    public String getLatitude(int position) {
        Museum museum = mMuseumList.get(position);
        return museum.getLatitude();
    }
    public String getLongtitude(int position){
        Museum museum = mMuseumList.get(position);
        return museum.getLongtitude();
    }
}
