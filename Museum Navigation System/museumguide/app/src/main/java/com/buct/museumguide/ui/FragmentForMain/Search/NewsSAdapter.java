package com.buct.museumguide.ui.FragmentForMain.Search;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.buct.museumguide.R;
import com.buct.museumguide.bean.News;
import com.buct.museumguide.ui.ClassForNews.WebViewer;
import com.bumptech.glide.Glide;

import java.util.List;

public class NewsSAdapter extends RecyclerView.Adapter<NewsSAdapter.ViewHolder> {

    private List<News> mNewsList;
    private Context mContext;
    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        TextView author;
        TextView museum;
        TextView tag;
        TextView time;
        ImageView image;

        public  ViewHolder (View view){
            super(view);
            title=view.findViewById(R.id.search_news_title);
            author=view.findViewById(R.id.search_news_author);
            museum=view.findViewById(R.id.search_news_museum);
            tag=view.findViewById(R.id.search_news_tag);
            time=view.findViewById(R.id.search_news_time);
            image=view.findViewById(R.id.search_news_iamge);
        }
    }



    public NewsSAdapter(Context context,List<News> newsList){
        mNewsList=newsList;
        mContext=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.search_news_item,parent,false);
        final ViewHolder holder=new ViewHolder(view);


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position=holder.getAdapterPosition();//5.
                String url=mNewsList.get(position).getUrl();
                Intent intent=new Intent(mContext, WebViewer.class);
                intent.putExtra("uri",url);
                mContext.startActivity(intent);
                Toast.makeText(mContext, "我被点击了", Toast.LENGTH_SHORT).show();
            }
        });

        return  holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        News exhibition=mNewsList.get(position);
        holder.title.setText(exhibition.getTitle());
        holder.author.setText("作者： "+exhibition.getContent());
        holder.museum.setText("博物馆： "+exhibition.getAuthor());
        holder.time.setText("时间： "+exhibition.getTime());
//        holder.tag.setText("标签： "+exhibition.getTag());
//        Uri uri = Uri.fromFile(new File(exhibition.getImgUrl()));
//        holder.image.setImageURI(uri);
        Glide.with(holder.itemView)
                .load(exhibition.getImgUrl())
                .into(holder.image);
        holder.itemView.setTag(mNewsList.get(position).getId());

    }

    @Override
    public int getItemCount() {

        return mNewsList.size();

    }
}