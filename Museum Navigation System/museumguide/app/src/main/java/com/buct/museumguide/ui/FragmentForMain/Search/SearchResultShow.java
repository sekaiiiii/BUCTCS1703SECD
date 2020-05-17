package com.buct.museumguide.ui.FragmentForMain.Search;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.buct.museumguide.R;
import com.buct.museumguide.Service.CollectionMsg;
import com.buct.museumguide.Service.CollectionResultMsg;
import com.buct.museumguide.Service.CommandRequest;
import com.buct.museumguide.Service.EducationResultMsg;
import com.buct.museumguide.Service.ExhibitionResultMsg;
import com.buct.museumguide.Service.MuseumInfoResultMsg;
import com.buct.museumguide.Service.NewsResultMsg;
import com.buct.museumguide.Service.StateBroadCast;
import com.buct.museumguide.bean.Collection;
import com.buct.museumguide.bean.Education;
import com.buct.museumguide.bean.Exhibition;
import com.buct.museumguide.bean.Museum;
import com.buct.museumguide.bean.News;
import com.buct.museumguide.ui.News.DashboardViewModel;
import com.buct.museumguide.ui.News.NewsRecyclerAdapter;
import com.buct.museumguide.util.RequestHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;


public class SearchResultShow extends Fragment {


    private ImageButton back;
    private TextView textView;

    private String content;
    private String type;
    private SearchResultShowViewModel searchResultShowViewModel;
    RequestHelper requestHelper;

    private ExhibitionAdapter exhibitionAdapter;
    private ArrayList<Exhibition> exhibitionList=new ArrayList<>();

    private EducationAdapter educationAdapter;
    private ArrayList<Education> educationList=new ArrayList<>();

    private CollectionAdapter collectionAdapter;
    private ArrayList<Collection> collectionList=new ArrayList<>();

    private MuseumSAdapter museumSAdapter;
    private ArrayList<Museum> museumList=new ArrayList<>();

    private NewsSAdapter newsSAdapter;
    private ArrayList<News> newsList=new ArrayList<>();

    private NoInfoAdapter noInfoAdapter;
    private ArrayList<String> noList=new ArrayList<>();

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);

    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.search_result_show, container, false);

        textView=view.findViewById(R.id.searchResult_textView);
        back=view.findViewById(R.id.searchResult_imageButton1);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).popBackStack();
            }
        });


        Bundle bundle=getArguments();
        content=bundle.getString("content");
        type=bundle.getString("type");

        textView.setText(type);
        searchResultShowViewModel=
                ViewModelProviders.of(this).get(SearchResultShowViewModel.class);

        RecyclerView recyclerView=view.findViewById(R.id.search_result_recylerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        if(type.equals("展览")){
//            if(exhibitionList.size()==0) {
//                noList.add("first");
//                noInfoAdapter=new NoInfoAdapter(noList);
//                recyclerView.setAdapter(noInfoAdapter);
//            }
//            else
//            {
                exhibitionAdapter =new ExhibitionAdapter(exhibitionList);
                recyclerView.setAdapter(exhibitionAdapter);
//            }
        }
        else if(type.equals("教育活动")){
//            if(educationList.size()==0) {
//                noList.add("first");
//                noInfoAdapter=new NoInfoAdapter(noList);
//                recyclerView.setAdapter(noInfoAdapter);
//            }
//            else{
                educationAdapter =new EducationAdapter(educationList);
                recyclerView.setAdapter(educationAdapter);
//            }
        }
        else if(type.equals("藏品")){
//            if(collectionList.size()==0) {
//                noList.add("first");
//                noInfoAdapter=new NoInfoAdapter(noList);
//                recyclerView.setAdapter(noInfoAdapter);
//            }
//            else{
                collectionAdapter= new CollectionAdapter(collectionList);
                recyclerView.setAdapter(collectionAdapter);
//            }
        }
        else if(type.equals("博物馆")){
//            if(museumList.size()==0) {
//                noList.add("first");
//                noInfoAdapter=new NoInfoAdapter(noList);
//                recyclerView.setAdapter(noInfoAdapter);
//            }
//            else{
                museumSAdapter=new MuseumSAdapter(museumList);
                recyclerView.setAdapter(museumSAdapter);
//            }
        }
        else if(type.equals("新闻")){
//            if(newsList.size()==0) {
//                noList.add("first");
//                noInfoAdapter=new NoInfoAdapter(noList);
//                recyclerView.setAdapter(noInfoAdapter);
//            }else{
                newsSAdapter=new NewsSAdapter(getContext(),newsList);
                recyclerView.setAdapter(newsSAdapter);
//            }
        }

        return view;
    }

//    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//    @Subscribe(sticky = true)
//    public void GetState(StateBroadCast msg) {
//        if (msg.state == 1) {
//            System.out.println("收到了服务已启动的通知");
//            requestHelper.getNews(Objects.requireNonNull(getActivity()), -1, "");
//        } else {
//            EventBus.getDefault()
//                    .post(new
//                            CommandRequest
//                            ("http://192.144.239.176:8080/api/android/get_new_info"));
//        }
//    }

    //接收展览
    @Subscribe(sticky = true)
    public void recieveExhibition(ExhibitionResultMsg exhibitionResultMsg){
        String responseData = exhibitionResultMsg.res;
        Log.d("Exhibition", responseData);
        try {
            JSONObject jsonObject = new JSONObject(responseData);
            String state = String.valueOf(jsonObject.get("status"));
            parseJSONWithJSONObject_exhibition(String.valueOf(jsonObject.getJSONObject("data").get("exhibition_list")));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void parseJSONWithJSONObject_exhibition(String jsonData) throws JSONException {
        try {
            Log.d("Exhibition", "onReceiveNews: " + jsonData);
            JSONArray jsonArray = new JSONArray(jsonData);
            for(int i=0;i<jsonArray.length();++i) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                exhibitionList.add(new Exhibition(jsonObject));
//                for(Exhibition exhibition:exhibitionList){
//                    Log.d("Exhibition",exhibition.getName());
//                }
            }
            Log.d("Exhibition",String.valueOf(exhibitionList.size()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //接收教育活动
    @Subscribe(sticky = true)
    public void recieveEducation(EducationResultMsg educationResulttMsg){
        String responseData = educationResulttMsg.res;
        Log.d("Exhibition", responseData);
        try {
            JSONObject jsonObject = new JSONObject(responseData);
            String state = String.valueOf(jsonObject.get("status"));
            parseJSONWithJSONObject_education(String.valueOf(jsonObject.getJSONObject("data").get("education_activity_list")));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void parseJSONWithJSONObject_education(String jsonData) throws JSONException {
        try {
            Log.d("Exhibition", "onReceiveNews: " + jsonData);
            JSONArray jsonArray = new JSONArray(jsonData);
            for(int i=0;i<jsonArray.length();++i) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                educationList.add(new Education(jsonObject));
//                for(Exhibition exhibition:exhibitionList){
//                    Log.d("Exhibition",exhibition.getName());
//                }
            }
            Log.d("Exhibition",String.valueOf(educationList.size()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //接收藏品
    @Subscribe(sticky = true)
    public void recieveCollection(CollectionResultMsg collectionResultMsg){
        String responseData =collectionResultMsg.res;
        Log.d("Exhibition", responseData);
        try {
            JSONObject jsonObject = new JSONObject(responseData);
            String state = String.valueOf(jsonObject.get("status"));
            parseJSONWithJSONObject_collection(String.valueOf(jsonObject.getJSONObject("data").get("collection_list")));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void parseJSONWithJSONObject_collection(String jsonData) throws JSONException {
        try {
            Log.d("Exhibition", "onReceiveNews: " + jsonData);
            JSONArray jsonArray = new JSONArray(jsonData);
            for(int i=0;i<jsonArray.length();++i) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                collectionList.add(new Collection(jsonObject));
//                for(Exhibition exhibition:exhibitionList){
//                    Log.d("Exhibition",exhibition.getName());
//                }
            }
            Log.d("Exhibition",String.valueOf(collectionList.size()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //接博物馆
    @Subscribe(sticky = true)
    public void recievemuseum(MuseumInfoResultMsg museumInfoResultMsg){
        String responseData = museumInfoResultMsg.res;
        Log.d("Exhibition", responseData);
        try {
            JSONObject jsonObject = new JSONObject(responseData);
            String state = String.valueOf(jsonObject.get("status"));
            parseJSONWithJSONObject_museum(String.valueOf(jsonObject.getJSONObject("data").get("museum_list")));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void parseJSONWithJSONObject_museum(String jsonData) throws JSONException {
        try {
            Log.d("Exhibition", "onReceiveNews: " + jsonData);
            JSONArray jsonArray = new JSONArray(jsonData);
            for(int i=0;i<jsonArray.length();++i) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                museumList.add(new Museum(jsonObject));
//                for(Exhibition exhibition:exhibitionList){
//                    Log.d("Exhibition",exhibition.getName());
//                }
            }
            Log.d("Exhibition",String.valueOf(museumList.size()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //接新闻
    @Subscribe(sticky = true)
    public void recievenews(NewsResultMsg newsResultMsg){
        String responseData = newsResultMsg.res;
        Log.d("Exhibition", responseData);
        try {
            JSONObject jsonObject = new JSONObject(responseData);
            String state = String.valueOf(jsonObject.get("status"));
            parseJSONWithJSONObject_news(String.valueOf(jsonObject.getJSONObject("data").get("data")));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void parseJSONWithJSONObject_news(String jsonData) throws JSONException {
        try {
            Log.d("Exhibition", "onReceiveNews: " + jsonData);
            JSONArray jsonArray = new JSONArray(jsonData);
            for(int i=0;i<jsonArray.length();++i) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                newsList.add(new News(jsonObject));
//                for(Exhibition exhibition:exhibitionList){
//                    Log.d("Exhibition",exhibition.getName());
//                }
            }
            Log.d("Exhibition",String.valueOf(newsList.size()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
