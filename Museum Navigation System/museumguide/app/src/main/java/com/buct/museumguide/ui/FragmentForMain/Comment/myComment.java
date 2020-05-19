package com.buct.museumguide.ui.FragmentForMain.Comment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.buct.museumguide.R;
import com.buct.museumguide.util.WebHelper;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class myComment extends Fragment {

    private MyCommentViewModel mViewModel;
    private SharedPreferences info;
    public static myComment newInstance() {
        return new myComment();
    }

    private RatingBar rating1,rating2,rating3;
    private TextView show1,show2,show3;
    private Button submit;
    private String museumID;
    public myComment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.my_comment_fragment, container, false);


        //返回
        ImageButton imageButton=(ImageButton) view.findViewById(R.id.back_commit);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).popBackStack();
            }
        });
        info = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
        //评星
        rating1=view.findViewById(R.id.Rating1);
        rating2=view.findViewById(R.id.Rating2);
        rating3=view.findViewById(R.id.Rating3);
        show1=view.findViewById(R.id.RatingShow1);
        show2=view.findViewById(R.id.RatingShow2);
        show3=view.findViewById(R.id.RatingShow3);

        rating1.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                String tmp="展览: ";String tmp2=String.valueOf(v);
                tmp=tmp+tmp2+"  ";
                show1.setText(tmp);
            }
        });

        rating2.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                String tmp="环境: ";String tmp2=String.valueOf(v);
                tmp=tmp+tmp2+"  ";
                show2.setText(tmp);
            }
        });

        rating3.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                String tmp="服务: ";String tmp2=String.valueOf(v);
                tmp=tmp+tmp2+"  ";
                show3.setText(tmp);
            }
        });
        //评论框
        EditText edtMsg = (EditText)view.findViewById(R.id.submit_commit);
        edtMsg.setScrollbarFadingEnabled(false);

        submit=view.findViewById(R.id.button_submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtMsg.getText().toString().equals("")){
                    Toast.makeText(getContext(),"请输入评论再提交",Toast.LENGTH_SHORT).show();
                }
                else {
                    String postMessage;postMessage=edtMsg.getText().toString();
                    Log.d("getComment","postmessage: "+postMessage);
                    MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                    OkHttpClient client = new OkHttpClient();
                    String url="http://192.144.239.176:8080/api/android/comment";
                    HashMap<String,String> map=new HashMap<>();
                    map.put("museum_id",info.getInt("curMuseumId",1)+"");
                    map.put("content",postMessage);
                    map.put("exhibition_score",rating1.getRating()+"");
                    map.put("environment_score",rating2.getRating()+"");
                    map.put("service_score",rating3.getRating()+"");
                    String cookie;
                    cookie= WebHelper.getCookie(getActivity());
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
                                        .header("cookie",cookie)
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
                    Toast.makeText(getContext(),"评论成功",Toast.LENGTH_SHORT).show();

                }
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        mViewModel = ViewModelProviders.of(this).get(MyCommentViewModel.class);
        // TODO: Use the ViewModel
    }

}
