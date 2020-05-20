package com.buct.museumguide.ui.FragmentForMain.Search;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.buct.museumguide.R;
import com.buct.museumguide.Service.CommandRequest;
import com.buct.museumguide.Service.StateBroadCast;
import com.buct.museumguide.ui.News.NewsRecyclerAdapter;
import com.buct.museumguide.util.RequestHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.Objects;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SearchResult extends Fragment {

    private SearchResultViewModel mViewModel;
    private RequestHelper requestHelper;
    private String content;
    private String res;

    private Handler handler=new Handler(){

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if(msg.what==100){
                res=(String)msg.obj;

            }
        }
    };

    public static SearchResult newInstance() {
        return new SearchResult();
    }


//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        EventBus.getDefault().register(this);
//    }
//
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        EventBus.getDefault().unregister(this);
//    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        mViewModel =
                ViewModelProviders.of(this).get(SearchResultViewModel.class);
        View root = inflater.inflate(R.layout.search_result_fragment, container, false);


        //搜索
        ImageButton buttons=root.findViewById(R.id.button_search);
        buttons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        //返回
        Button cancel=root.findViewById(R.id.cancel_search);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).popBackStack();
            }
        });

        res="NoFlag";
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String type;
        EditText editText=view.findViewById(R.id.search_editText);
        //下拉框
        Spinner spinner=view.findViewById(R.id.spinner_search_choice);


        //搜索
        ImageButton buttons=view.findViewById(R.id.button_search);
        buttons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController controller=Navigation.findNavController(requireView());
                content=editText.getText().toString();
                String type;
                if(content.equals("")){
                    Toast.makeText(getContext(),"请输入内容再进行搜索",Toast.LENGTH_SHORT).show();
                }
                else{
                    type=spinner.getSelectedItem().toString();

                    if(type.equals("展览")){
                        getExhibition(content);
                    }
                    else if(type.equals("博物馆")){
                        getMuseum(content);
                    }
                    else if(type.equals("教育活动")){
                        getEducation(content);
                    }
                    else if(type.equals("藏品")){
                        getCollection(content);
                    }
                    else if(type.equals("新闻")){
                        getNews(content);
                    }
                    if(!res.equals("NoFlag")){
                        Bundle bundle = new Bundle();
                        bundle.putString("type",type);
                        bundle.putString("content",res);
                        res="NoFlag";
                        Navigation.findNavController(view).navigate(R.id.action_searchResult_to_searchResultShow,bundle);
                    }
                }

            }
        });


        //热搜
        Button hot1=view.findViewById(R.id.Button_hot1);
        hot1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText.setText("国家博物馆");
            }
        });
        Button hot2=view.findViewById(R.id.Button_hot2);
        hot2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText.setText("后母戊鼎");
            }
        });


        Button hot3=view.findViewById(R.id.Button_hot3);
        hot3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText.setText("故宫博物院");
            }
        });

        Button hot4=view.findViewById(R.id.Button_hot4);
        hot4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText.setText("黄玉夔龙纹扁壶");
            }
        });

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(SearchResultViewModel.class);
        // TODO: Use the ViewModel
    }

    void getExhibition(String search){
        String url="http://192.144.239.176:8080/api/android/get_exhibition_info?ppn=100&name=";
        String uri=url+search;
        OkHttpClient client=new OkHttpClient();
        new Thread(new Runnable() {
            @Override
            public void run()  {
                Request request=new Request.Builder()
                        .get()
                        .url(uri)
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    Message message=new Message();
                    message.what=100;
                    message.obj=response.body().string();

                    handler.sendMessage(message);
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }
        }).start();

    }

    void getMuseum(String search){
        String url="http://192.144.239.176:8080/api/android/get_museum_info?name=";
        String uri=url+search;
        OkHttpClient client=new OkHttpClient();
        new Thread(new Runnable() {
            @Override
            public void run()  {
                Request request=new Request.Builder()
                        .get()
                        .url(uri)
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    Message message=new Message();
                    message.what=100;
                    message.obj=response.body().string();
//                    Log.d("getMuseum",(String)message.obj);
                    handler.sendMessage(message);
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    void getEducation(String search){
        String url="http://192.144.239.176:8080/api/android/get_education_activity_info?ppn=100&name=";
        String uri=url+search;
        OkHttpClient client=new OkHttpClient();
        new Thread(new Runnable() {
            @Override
            public void run()  {
                Request request=new Request.Builder()
                        .get()
                        .url(uri)
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    Message message=new Message();
                    message.what=100;
                    message.obj=response.body().string();
                    handler.sendMessage(message);
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    void getCollection(String search){
        String url="http://192.144.239.176:8080/api/android/get_collection_info?ppn=100&name=";
        String uri=url+search;
        OkHttpClient client=new OkHttpClient();
        new Thread(new Runnable() {
            @Override
            public void run()  {
                Request request=new Request.Builder()
                        .get()
                        .url(uri)
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    Message message=new Message();
                    message.what=100;
                    message.obj=response.body().string();
                    handler.sendMessage(message);
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    void getNews(String search){
        String url="http://192.144.239.176:8080/api/android/get_new_info?ppn=100&name=";
        String uri=url+search;
        OkHttpClient client=new OkHttpClient();
        new Thread(new Runnable() {
            @Override
            public void run()  {
                Request request=new Request.Builder()
                        .get()
                        .url(uri)
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    Message message=new Message();
                    message.what=100;
                    message.obj=response.body().string();
                    handler.sendMessage(message);
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }
        }).start();
    }


}
