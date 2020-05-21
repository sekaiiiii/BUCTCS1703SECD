package com.buct.museumguide.ui.FragmentForUsers.Upload;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.buct.museumguide.R;
import com.race604.drawable.wave.WaveDrawable;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ShowUploadAdapter_showonly extends RecyclerView.Adapter<ShowUploadAdapter_showonly.ViewHolder> {
    private List<audioitem> list;
    public ShowUploadAdapter_showonly.MyClick click;
    private Context context;
    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        TextView name;
        TextView author;
        public ViewHolder (View view)
        {    super(view);
            title=view.findViewById(R.id.textView16);
            name=view.findViewById(R.id.textView17);
            author=view.findViewById(R.id.textView18);
        }

    }
    public void setClick(ShowUploadAdapter_showonly.MyClick click){
        this.click = click;
    }

    public interface MyClick{
        void click(View v);
    }
    public ShowUploadAdapter_showonly(List<audioitem> l,Context context){
        this.list=l;
        this.context=context;
    }
    @NonNull
    @Override
    public ShowUploadAdapter_showonly.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.audioshouw_item,parent,false);
        ShowUploadAdapter_showonly.ViewHolder holder = new ShowUploadAdapter_showonly.ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(click != null){
                    click.click(v);
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ShowUploadAdapter_showonly.ViewHolder holder, int position) {
        audioitem item=list.get(position);
        holder.title.setText(item.getTitle());
        holder.name.setText(item.getFilename());
        holder.author.setText(item.getId());
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
}
