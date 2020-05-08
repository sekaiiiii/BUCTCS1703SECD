package com.buct.museumguide.ui.FragmentForUsers.Upload;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.buct.museumguide.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ShowUploadAdapter extends RecyclerView.Adapter<ShowUploadAdapter.ViewHolder> {
    private List<audioitem> list;
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
    ShowUploadAdapter(List<audioitem>l){this.list=l;}
    @NonNull
    @Override
    public ShowUploadAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_useraudios,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ShowUploadAdapter.ViewHolder holder, int position) {
            audioitem item=list.get(position);
            holder.title.setText(item.getTitle());
            holder.name.setText(item.getFilename());
            holder.author.setText(item.getAuthor());
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
}
