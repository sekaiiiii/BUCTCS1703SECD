package com.buct.museumguide.ui.FragmentForUsers.Upload;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.media.Image;
import android.os.Build;
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
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

public class ShowUploadAdapter extends RecyclerView.Adapter<ShowUploadAdapter.ViewHolder> {
    private List<audioitem> list;
    public MyClick click;
    private Context context;
    private int count;
    private int length;
    private WaveDrawable mWaveDrawable;
    private AnimationDrawable playerAnimation;
    public List<Boolean>isture;
    private final int MAX_LEN=10000;
    private final int MID_LEN=5000;
    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        ImageView imageView;

        public ViewHolder (View view)
        {    super(view);
            title=view.findViewById(R.id.textView16);
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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull ShowUploadAdapter.ViewHolder holder, int position) {
            audioitem item=list.get(position);
            holder.title.setText(item.getTitle());
         mWaveDrawable = new WaveDrawable(context.getDrawable(R.drawable.continue1));
        holder.imageView.setBackgroundResource(R.drawable.amiation);
        playerAnimation= (AnimationDrawable) holder.imageView.getBackground();
        /**/if(isture.get(position)==true)
            playerAnimation.stop();
        else
            playerAnimation.start();
        System.out.println(isture.size());
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
}
