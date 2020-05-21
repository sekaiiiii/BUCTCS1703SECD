package com.buct.museumguide.ui.FragmentForMain.Search;

import android.text.TextPaint;
import android.util.Log;
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

import org.json.JSONArray;
import org.json.JSONException;

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
        holder.name.setText(exhibition.getName());
        TextPaint tp=holder.name.getPaint();tp.setFakeBoldText(true);
        holder.materials.setText("材质： "+exhibition.getMaterial());
        holder.museum.setText("博物馆： "+"故宫博物院");
//        holder.tag.setText("标签： "+exhibition.getTag());
//        holder.content.setText("内容： "+exhibition.getContent());
//        Uri uri = Uri.fromFile(new File(exhibition.getImgUrl()));
//        holder.image.setImageURI(uri);
        String imageUrl="";

        try{
            JSONArray imageArray=exhibition.getImage_list();
            Log.d("geturl",imageArray.toString());
            String imageShow;
            imageShow=imageArray.getString(0);

            Log.d("geturl","imageShow");
            imageUrl="http://192.144.239.176:8080/"+imageShow.toString();
        }
        catch (JSONException e){
            e.printStackTrace();
            Log.d("geturl",e.toString());
        }
        Log.d("geturl",imageUrl);
        if(!imageUrl.equals("")){
            Glide.with(holder.itemView)
                    .load(imageUrl)
                    .into(holder.image);
        }

    }

    @Override
    public int getItemCount() {

        return mCollectionList.size();

    }
}