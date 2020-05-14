package com.buct.museumguide.ui.FragmentForUsers.Upload;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.buct.museumguide.R;
import com.race604.drawable.wave.WaveDrawable;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ShowUploadAdapter extends RecyclerView.Adapter<ShowUploadAdapter.ViewHolder> {
    private List<audioitem> list;
    public MyClick click;
    private Context context;
    private int count;
    private int length;
    private WaveDrawable mWaveDrawable;
    public List<Boolean>isture;
    private final int MAX_LEN=10000;
    private final int MID_LEN=5000;
    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        TextView name;
        TextView author;
        ImageView imageView;

        public ViewHolder (View view)
        {    super(view);
            title=view.findViewById(R.id.textView16);
            name=view.findViewById(R.id.textView17);
            author=view.findViewById(R.id.textView18);
            imageView=view.findViewById(R.id.imageView);
        }

    }
    public void setClick(MyClick click){
        this.click = click;
    }

    public interface MyClick{
        void click(View v);
    }
    public ShowUploadAdapter(List<audioitem> l,Context context){
        this.list=l;
        System.out.println("初始化0"+l.size());
        this.isture=new ArrayList<>();
        for(int i=0;i<10000;i++)isture.add(true);
        System.out.println("初始化"+isture.size());
        this.context=context;
        count=0;
        this.length=10000;
    }
    @NonNull
    @Override
    public ShowUploadAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_useraudios,parent,false);
        ViewHolder holder = new ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(click != null){
                    count++;
                    click.click(v);
                    if(count%2==1)
                    length=5000;
                    else
                        length=10000;
                    notifyDataSetChanged();
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ShowUploadAdapter.ViewHolder holder, int position) {
            audioitem item=list.get(position);
            holder.title.setText(item.getTitle());
            holder.name.setText(item.getFilename());
            holder.author.setText(item.getAuthor());
         mWaveDrawable = new WaveDrawable(context,R.color.colorBrown);
        holder.imageView.setImageDrawable(mWaveDrawable);
        /**/if(isture.get(position)==true)
            mWaveDrawable.setLevel(10000);
        else
            mWaveDrawable.setLevel(5000);
        System.out.println(isture.size());
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
}
