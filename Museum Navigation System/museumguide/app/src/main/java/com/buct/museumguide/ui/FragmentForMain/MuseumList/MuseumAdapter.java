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

import java.util.List;

public class MuseumAdapter extends RecyclerView.Adapter<MuseumAdapter.ViewHolder> {
    private Context context;
    private List<Museum> mMuseumList;
    private MuseumAdapter.OnItemClickListener onItemClickListener;
    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView museumImage;
        TextView museumName;
        TextView museumLevel;
        TextView museumNumber;
        CardView cardView;
        TextView museumTest;
        public ViewHolder(View view){
            super(view);
            museumImage = view.findViewById(R.id.museum_image);
            museumName = view.findViewById(R.id.museum_name);
            museumNumber = view.findViewById(R.id.museum_number);
            museumLevel = view.findViewById(R.id.museum_level);
            cardView = view.findViewById(R.id.museumList_card_view);
            //museumTest = view.findViewById(R.id.museum_test);
        }
    }


    public MuseumAdapter(List<Museum> museumList){

        mMuseumList = museumList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.museum_items,parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Museum museum = mMuseumList.get(position);
        holder.museumName.setText(museum.getName());
        holder.museumImage.setImageResource(museum.getImageId());
        holder.museumLevel.setText(museum.getLevel());
        holder.museumNumber.setText(museum.getNumber());
        //holder.museumTest.setText(museum.getTest());
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
        void onItemLongClick(View view,int position);
    }
    public void setOnItemClickListener(MuseumAdapter.OnItemClickListener listener){
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

}
