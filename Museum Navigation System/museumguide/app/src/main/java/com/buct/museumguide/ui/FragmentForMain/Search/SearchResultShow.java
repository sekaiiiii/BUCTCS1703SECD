package com.buct.museumguide.ui.FragmentForMain.Search;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
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
import com.buct.museumguide.ui.FragmentForMain.CollectionDetailsViewModel;
import com.buct.museumguide.ui.FragmentForMain.ExhibitionDetailsViewModel;
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
    private CollectionDetailsViewModel mCollectionDetailsViewModel;
    private ExhibitionDetailsViewModel mExhibitionDetailsViewModel;
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


    }

    @Override
    public void onStop() {
        super.onStop();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mExhibitionDetailsViewModel =new ViewModelProvider(Objects.requireNonNull(getActivity())).get(ExhibitionDetailsViewModel.class);
        mCollectionDetailsViewModel = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(CollectionDetailsViewModel.class);
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

        type="NO";
        Bundle bundle=getArguments();
        content=bundle.getString("content");
        type=bundle.getString("type");
        Log.d("getContent",content);
        textView.setText(type);
        searchResultShowViewModel=
                ViewModelProviders.of(this).get(SearchResultShowViewModel.class);

        RecyclerView recyclerView=view.findViewById(R.id.search_result_recylerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        if(type.equals("展览")){
                parseJSONWithJSONObject_exhibition(content);
                    if(exhibitionList.size()==0) {
                        noList.add("first");
                        noInfoAdapter=new NoInfoAdapter(noList);
                        recyclerView.setAdapter(noInfoAdapter);
                    }
                    else
                    {
                        exhibitionAdapter =new ExhibitionAdapter(exhibitionList);
                        exhibitionAdapter.setOnItemClickListener(new ExhibitionAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                mExhibitionDetailsViewModel.setExhiLivaData(exhibitionList.get(position));
                                Navigation.findNavController(view).navigate(R.id.action_searchResultShow_to_exhibitionDetails);
                                Toast.makeText(getActivity(),"了解详情",Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onItemLongClick(View view, int position) {

                            }
                        });
                        recyclerView.setAdapter(exhibitionAdapter);
                    }
            Log.d("getContent",String.valueOf(exhibitionList.size()));

        }
        else if(type.equals("教育活动")){
            parseJSONWithJSONObject_education(content);
            if(educationList.size()==0) {
                noList.add("first");
                noInfoAdapter=new NoInfoAdapter(noList);
                recyclerView.setAdapter(noInfoAdapter);
            }
            else{
                educationAdapter =new EducationAdapter(educationList,getContext());
                recyclerView.setAdapter(educationAdapter);
            }
            Log.d("getContent",String.valueOf(educationList.size()));

        }
        else if(type.equals("藏品")){
            parseJSONWithJSONObject_collection(content);
            if(collectionList.size()==0) {
                noList.add("first");
                noInfoAdapter=new NoInfoAdapter(noList);
                recyclerView.setAdapter(noInfoAdapter);
            }
            else{
                collectionAdapter= new CollectionAdapter(collectionList,getActivity());
                collectionAdapter.setOnItemClickListener(new CollectionAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        mCollectionDetailsViewModel.setCollLivaData(collectionList.get(position));
                        Navigation.findNavController(view).navigate(R.id.action_searchResultShow_to_collectionDetails3);
                        Toast.makeText(getActivity(),"了解详情",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onItemLongClick(View view, int position) {

                    }
                });
                recyclerView.setAdapter(collectionAdapter);
            }
            Log.d("getContent",String.valueOf(collectionList.size()));
        }
        else if(type.equals("博物馆")){
            parseJSONWithJSONObject_museum(content);
            if(museumList.size()==0) {
                noList.add("first");
                noInfoAdapter=new NoInfoAdapter(noList);
                recyclerView.setAdapter(noInfoAdapter);
            }
            else{
                museumSAdapter=new MuseumSAdapter(museumList,getActivity());
                recyclerView.setAdapter(museumSAdapter);
            }
            Log.d("getContent",String.valueOf(museumList.size()));
        }
        else if(type.equals("新闻")){
            parseJSONWithJSONObject_news(content);
            if(newsList.size()==0) {
                noList.add("first");
                noInfoAdapter=new NoInfoAdapter(noList);
                recyclerView.setAdapter(noInfoAdapter);
            }else{
                newsSAdapter=new NewsSAdapter(getContext(),newsList);
                recyclerView.setAdapter(newsSAdapter);
            }
            Log.d("getContent",String.valueOf(newsList.size()));
        }
        return view;
    }


    //接收展览

    private void parseJSONWithJSONObject_exhibition(String jsonData)  {
        try {
            Log.d("Exhibition", "onReceiveNews: " + jsonData);
            JSONObject jsonObject1 = new JSONObject(jsonData);
            String state = String.valueOf(jsonObject1.get("status"));
            String s=String.valueOf(jsonObject1.getJSONObject("data").get("exhibition_list"));
            JSONArray jsonArray = new JSONArray(s);
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

    private void parseJSONWithJSONObject_education(String jsonData) {
        try {
            Log.d("Exhibition", "onReceiveNews: " + jsonData);
            JSONObject jsonObject1 = new JSONObject(jsonData);
            String state = String.valueOf(jsonObject1.get("status"));
            String s=String.valueOf(jsonObject1.getJSONObject("data").get("education_activity_list"));
            JSONArray jsonArray = new JSONArray(s);
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


    private void parseJSONWithJSONObject_collection(String jsonData) {
        try {
            Log.d("Exhibition", "onReceiveNews: " + jsonData);
            //            JSONObject jsonObject = new JSONObject(responseData);
            JSONObject jsonObject1 = new JSONObject(jsonData);
            String state = String.valueOf(jsonObject1.get("status"));
            String s=String.valueOf(jsonObject1.getJSONObject("data").get("collection_list"));
            JSONArray jsonArray = new JSONArray(s);
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

    private void parseJSONWithJSONObject_museum(String jsonData) {
        try {
            Log.d("Exhibition", "onReceiveNews: " + jsonData);
            JSONObject jsonObject1 = new JSONObject(jsonData);
            String state = String.valueOf(jsonObject1.get("status"));
            String s=String.valueOf(jsonObject1.getJSONObject("data").get("museum_list"));
            JSONArray jsonArray = new JSONArray(s);
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

    private void parseJSONWithJSONObject_news(String jsonData) {
        try {
            Log.d("Exhibition", "onReceiveNews: " + jsonData);
            JSONObject jsonObject1 = new JSONObject(jsonData);
            String state = String.valueOf(jsonObject1.get("status"));
            String s=String.valueOf(jsonObject1.getJSONObject("data").get("data"));
            JSONArray jsonArray = new JSONArray(s);
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
