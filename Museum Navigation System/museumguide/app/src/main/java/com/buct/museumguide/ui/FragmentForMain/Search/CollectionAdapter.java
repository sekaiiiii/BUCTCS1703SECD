package com.buct.museumguide.ui.FragmentForMain.Search;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.buct.museumguide.R;
import com.buct.museumguide.bean.Collection;
import com.bumptech.glide.Glide;

import java.util.List;

public class CollectionAdapter extends RecyclerView.Adapter< CollectionAdapter.ViewHolder> {

    private List<Collection> mCollectionList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView content;
        TextView materials;
        TextView museum;
        TextView tag;
        ImageView image;

        public  ViewHolder (View view){
            super(view);
            name=view.findViewById(R.id.search_collection_name);
            content=view.findViewById(R.id.search_collection_content);
            museum=view.findViewById(R.id.search_collection_museum);
            materials=view.findViewById(R.id.search_collection_material);
            tag=view.findViewById(R.id.search_collection_tag);
            image=view.findViewById(R.id.search_collection_iamge);
        }
    }



    public CollectionAdapter(List<Collection> exhibitionList){
        mCollectionList=exhibitionList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.search_collection_item,parent,false);
        final ViewHolder holder=new ViewHolder(view);
        return  holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Collection exhibition=mCollectionList.get(position);
        holder.name.setText("  "+exhibition.getName());
        holder.materials.setText("  材质： "+exhibition.getMaterial());
        holder.museum.setText("  博物馆： "+exhibition.getMuseum_id());
//        holder.tag.setText("标签： "+exhibition.getTag());
//        holder.content.setText("内容： "+exhibition.getContent());
//        Uri uri = Uri.fromFile(new File(exhibition.getImgUrl()));
//        holder.image.setImageURI(uri);
        Glide.with(holder.itemView)
                .load(exhibition.getImgUrl())
                .into(holder.image);

    }

    @Override
    public int getItemCount() {

        return mCollectionList.size();

    }
}