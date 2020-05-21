package com.buct.museumguide.ui.FragmentForMain.Search;

import android.content.Context;
import android.text.TextPaint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.buct.museumguide.R;
import com.buct.museumguide.bean.Collection;
import com.buct.museumguide.ui.FragmentForMain.CollectionDetailsViewModel;
import com.buct.museumguide.ui.FragmentForMain.CommonList.EduRecyclerAdapter;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;
import java.util.Objects;

public class CollectionAdapter extends RecyclerView.Adapter< CollectionAdapter.ViewHolder> {

    private List<Collection> mCollectionList;
    private Context mContext;
    private CollectionAdapter.OnItemClickListener onItemClickListener;
    private View mHeaderView;
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_NORMAL = 1;

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


    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView content;
        TextView materials;
        TextView museum;
        TextView tag;
        ImageView image;
        CardView cardView;

        public  ViewHolder (View view){
            super(view);

            name=view.findViewById(R.id.search_collection_name);
            content=view.findViewById(R.id.search_collection_content);
            museum=view.findViewById(R.id.search_collection_museum);
            materials=view.findViewById(R.id.search_collection_material);
            tag=view.findViewById(R.id.search_collection_tag);
            image=view.findViewById(R.id.search_collection_iamge);
            cardView=view.findViewById(R.id.cardViewCollections);
        }
    }


    public interface OnItemClickListener {
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }

    // ② 定义一个设置点击监听器的方法
    public void setOnItemClickListener(CollectionAdapter.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }
    public CollectionAdapter(List<Collection> exhibitionList,Context context){
        mCollectionList=exhibitionList;
        mContext=context;
    }

    private int getRealPosition(RecyclerView.ViewHolder holder) {
        int position = holder.getLayoutPosition();
        return mHeaderView == null ? position : position - 1;
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

        holder.cardView.setOnClickListener(v -> {
            if(onItemClickListener != null) {
                int pos1 = getRealPosition(holder);
                onItemClickListener.onItemClick(holder.cardView, pos1);
            }
        });
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