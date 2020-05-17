package com.buct.museumguide.ui.FragmentForMain.Comment;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.buct.museumguide.R;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

    private List<PerComment> mCommentList;
    private Context mcontext;

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView textViewName;
        TextView textViewMail;
        TextView textViewExhibition;
        TextView textViewTime;
        TextView textViewContent;
        TextView textViewService;
        TextView textViewEnvironment;
        Button changeComment;
        Button deleteComment;
        View commentView;
        public ViewHolder(View view){
            super(view);
            commentView=view;

            deleteComment=view.findViewById(R.id.comment_delete);
            textViewName=view.findViewById(R.id.comment_name);
            textViewMail=view.findViewById(R.id.comment_mail);
            textViewExhibition=view.findViewById(R.id.comment_exhibiton);
            textViewEnvironment=view.findViewById(R.id.comment_environment);
            textViewTime=view.findViewById(R.id.comment_time);
            textViewContent=view.findViewById(R.id.comment_content);
            textViewService=view.findViewById(R.id.comment_service);
        }
    }

    public CommentAdapter(List<PerComment>CommentList,Context context){

        mcontext=context;
        mCommentList=CommentList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item,parent,false);
        final ViewHolder holder=new ViewHolder(view);
        return  holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PerComment perComment=mCommentList.get(position);
        holder.textViewContent.setText(perComment.getContent());
        holder.textViewName.setText(perComment.getName());
        holder.textViewMail.setText("   mail: "+perComment.getMail_address());
        holder.textViewExhibition.setText("展览: "+perComment.getExhibition_score());
        holder.textViewService.setText("服务: "+perComment.getService_score());
        holder.textViewTime.setText(perComment.getTime());
        holder.textViewEnvironment.setText("环境: "+perComment.getEnvironment_score());
        holder.deleteComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(perComment.getName().equals("CHJ")){
                   removeData(position);
                }
                else
                    Toast.makeText(view.getContext(),"抱歉，您只能删除自己的评论",Toast.LENGTH_SHORT).show();
            }

        });
    }

    @Override
    public int getItemCount() {
        return mCommentList.size();
    }

    public void removeData(int position) {
        mCommentList.remove(position);
        //删除动画
        notifyItemRemoved(position);
        notifyDataSetChanged();

         MediaType JSON = MediaType.parse("application/json; charset=utf-8");
         OkHttpClient client = new OkHttpClient();
         String url="http://192.144.239.176:8080/api/android/del_comment";

         HashMap<String,String> map=new HashMap<>();
         map.put("id",mCommentList.get(position).getId());
        Gson gson=new Gson();
        String data=gson.toJson(map);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.d("PostComment","in");
                    RequestBody body = RequestBody.create(JSON,data);
                    Request request = new Request.Builder()
                            .url(url)
                            .post(body)
                            .build();
                    Response response = client.newCall(request).execute();
                    Log.d("PostComment",response.body().string());
                }
                catch (IOException e){
                    Log.d("PostComment",e.toString());
                }
            }
        }).start();
    }

}
