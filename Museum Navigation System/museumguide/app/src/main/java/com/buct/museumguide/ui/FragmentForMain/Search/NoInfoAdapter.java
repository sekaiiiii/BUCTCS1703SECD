package com.buct.museumguide.ui.FragmentForMain.Search;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.buct.museumguide.R;

import java.util.List;

public class NoInfoAdapter extends RecyclerView.Adapter<NoInfoAdapter.ViewHolder> {
    private List<String>mlist;
    class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        ImageView imageView;
        public ViewHolder(View view){
            super(view);
            textView=view.findViewById(R.id.search_no_textView);
            imageView=view.findViewById(R.id.search_no_image);
        }
    }
    public NoInfoAdapter(List<String> list){
        mlist=list;
        Log.d("Noinfo",String.valueOf(mlist.size()));
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.search_noinfo_item,parent,false);
        final ViewHolder holder=new ViewHolder(view);
        return  holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }
}
