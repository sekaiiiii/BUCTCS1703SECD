package com.buct.museumguide.ui.FragmentForMain.CommonList;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.buct.museumguide.R;
import com.buct.museumguide.Service.CollectionResultMsg;
import com.buct.museumguide.Service.CommandRequest;
import com.buct.museumguide.Service.EducationMsg;
import com.buct.museumguide.Service.EducationResultMsg;
import com.buct.museumguide.Service.ExhibitionMsg;
import com.buct.museumguide.Service.ExhibitionResultMsg;
import com.buct.museumguide.Service.NewsMsg;
import com.buct.museumguide.Service.NewsResultMsg;
import com.buct.museumguide.Service.StateBroadCast;
import com.buct.museumguide.bean.Collection;
import com.buct.museumguide.bean.Education;
import com.buct.museumguide.bean.Exhibition;
import com.buct.museumguide.bean.News;
import com.buct.museumguide.ui.ClassForNews.WebViewer;
import com.buct.museumguide.ui.FragmentForMain.CollectionDetailsViewModel;
import com.buct.museumguide.ui.FragmentForMain.EducationDetailsViewModel;
import com.buct.museumguide.ui.FragmentForMain.ExhibitionDetailsViewModel;
import com.buct.museumguide.ui.News.NewsRecyclerAdapter;
import com.google.gson.JsonObject;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class CommonList extends Fragment {
    public static final String TAG ="CommonList" ;
    private CommonListViewModel commonListViewModel;
    private ExhibitionDetailsViewModel exhibitionDetailsViewModel;
    private CollectionDetailsViewModel collectionDetailsViewModel;
    private EducationDetailsViewModel educationDetailsViewModel;
    private ArrayList<Exhibition> exhiList = new ArrayList<>();
    private ArrayList<Collection> collList = new ArrayList<>();
    private ArrayList<News> newsList = new ArrayList<>();
    private ArrayList<Education> eduList = new ArrayList<>();

    public static CommonList newInstance() {
        return new CommonList();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach: ");
        if(!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
        commonListViewModel = ViewModelProviders.of(this).get(CommonListViewModel.class);
        exhibitionDetailsViewModel = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(ExhibitionDetailsViewModel.class);
        collectionDetailsViewModel = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(CollectionDetailsViewModel.class);
        educationDetailsViewModel = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(EducationDetailsViewModel.class);
        Bundle bundle = getArguments();
        assert bundle != null;
        int showType = bundle.getInt("showType");
        Toast.makeText(getActivity(), "" + showType,Toast.LENGTH_SHORT).show();

        View root = inflater.inflate(R.layout.common_list_fragment, container, false);
        TextView notFind = root.findViewById(R.id.notFind);
        ImageView commonListNotFind = root.findViewById(R.id.commonListNotFind);
        ImageButton topNavReturn = (ImageButton) root.findViewById(R.id.topNavReturn);
        topNavReturn.setOnClickListener(view -> Navigation.findNavController(view).popBackStack());
        TextView topNavTitle = root.findViewById(R.id.topNavTitle);

        RecyclerView commonList = root.findViewById(R.id.commonList);
        LinearLayoutManager linearLayoutManager;
        StaggeredGridLayoutManager gridLayoutManager;
        if(showType == 2) {
            gridLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
            commonList.setLayoutManager(gridLayoutManager);
        }
        else {
            linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            commonList.setLayoutManager(linearLayoutManager);
        }
        commonList.setItemAnimator(new DefaultItemAnimator());
//        commonListViewModel.getAllData(getActivity());
        switch (showType) {
            case 1:
                topNavTitle.setText("展览");
                ExhiRecyclerAdapter exhiAdapter = new ExhiRecyclerAdapter();
                commonList.setAdapter(exhiAdapter);
                exhiAdapter.addDatas(exhiList);
                exhiAdapter.setOnItemClickListener(new ExhiRecyclerAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Log.d(TAG, "onItemClick: " + exhiList.get(position).getName());
                        exhibitionDetailsViewModel.setExhiLivaData(exhiList.get(position));
                        Navigation.findNavController(getView()).navigate(R.id.action_commonList_to_exhibitionDetails);
                    }
                    @Override
                    public void onItemLongClick(View view, int position) {
                    }
                });
                break;
            case 2:
                topNavTitle.setText("馆藏精品");
                CollRecyclerAdapter collAdapter = new CollRecyclerAdapter();
                commonList.setAdapter(collAdapter);
                collAdapter.addDatas(collList);
                collAdapter.setOnItemClickListener(new CollRecyclerAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        collectionDetailsViewModel.setCollLivaData(collList.get(position));
                        Navigation.findNavController(root).navigate(R.id.action_commonList_to_collectionDetails);
                    }
                    @Override
                    public void onItemLongClick(View view, int position) {
                    }
                });
                break;
            case 3:
                topNavTitle.setText("馆内热闻");
                NewsRecyclerAdapter newsAdapter = new NewsRecyclerAdapter();
                commonList.setAdapter(newsAdapter);
//                newsList = News.getTestData();
                newsAdapter.addDatas(newsList);
                newsAdapter.setOnItemClickListener(new NewsRecyclerAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        //MainActivity.url=
                        Toast.makeText(getActivity(),newsList.get(position).getUrl(), Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(getActivity(), WebViewer.class);
                        intent.putExtra("uri",newsList.get(position).getUrl());
                        startActivity(intent);
                    }

                    @Override
                    public void onItemLongClick(View view, int position) {
                    }
                });
                break;
            default:
                topNavTitle.setText("教育活动");
                EduRecyclerAdapter eduAdapter = new EduRecyclerAdapter();
                commonList.setAdapter(eduAdapter);
                eduAdapter.addDatas(eduList);
                eduAdapter.setOnItemClickListener(new EduRecyclerAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        if(eduList.get(position).getUrl().equals("")) {
                            Toast.makeText(getActivity(), "暂无详细信息", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Intent intent = new Intent(getActivity(), WebViewer.class);
                            intent.putExtra("uri", eduList.get(position).getUrl());
                            startActivity(intent);
                        }
//                        Navigation.findNavController(getView()).navigate(R.id.action_commonList_to_educationDetails);
                    }
                    @Override
                    public void onItemLongClick(View view, int position) {
                    }
                });
                break;
        }
        if(showType == 1 && exhiList.size() == 0 || showType == 2 && collList.size() ==0 || showType == 3 && newsList.size() == 0 || showType == 4 && eduList.size() == 0) {
            commonList.setVisibility(View.GONE);
        }
        else {
            notFind.setVisibility(View.GONE);
            commonListNotFind.setVisibility(View.GONE);
        }
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
//        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        Log.d(TAG, "onStop: ");
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(sticky = true)
    public void onReceiveExhibition(ExhibitionResultMsg exhibitionResultMsg) {
        String responseData = exhibitionResultMsg.res;
        Log.d(TAG, "onReceiveExhibition: " + responseData);
        try {
            JSONObject jsonObject = new JSONObject(responseData);
            String state = String.valueOf(jsonObject.get("status"));
            parseJSONWithJSONObject(1, String.valueOf(jsonObject.getJSONObject("data").get("exhibition_list")));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Subscribe(sticky = true)
    public void onReceiveCollection(CollectionResultMsg collectionResultMsg) {
        String responseData = collectionResultMsg.res;
        Log.d(TAG, "onReceiveCollection: " + responseData);
        try {
            JSONObject jsonObject = new JSONObject(responseData);
            String state = String.valueOf(jsonObject.get("status"));
            parseJSONWithJSONObject(2, String.valueOf(jsonObject.getJSONObject("data").get("collection_list")));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Subscribe(sticky = true)
    public void onReceiveNews(NewsResultMsg newsResultMsg) {
        String responseData = newsResultMsg.res;
        Log.d(TAG, "onReceiveNews: " + responseData);
        try {
            JSONObject jsonObject = new JSONObject(responseData);
            String state = String.valueOf(jsonObject.get("status"));
            parseJSONWithJSONObject(3, String.valueOf(jsonObject.getJSONObject("data").get("data")));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Subscribe(sticky = true)
    public void onReceiveEducation(EducationResultMsg educationResultMsg) {
        String responseData = educationResultMsg.res;
        Log.d(TAG, "onReceiveEducation: " + responseData);
        try {
            JSONObject jsonObject = new JSONObject(responseData);
            String state = String.valueOf(jsonObject.get("status"));
            parseJSONWithJSONObject(4, String.valueOf(jsonObject.getJSONObject("data").get("education_activity_list")));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void parseJSONWithJSONObject(int type, String jsonData) {
        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            for(int i=0;i<jsonArray.length();++i) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                switch (type) {
                    case 1:
                        exhiList.add(new Exhibition(jsonObject));
                        break;
                    case 2:
                        collList.add(new Collection(jsonObject));
                        break;
                    case 3:
                        newsList.add(new News(jsonObject));
                        break;
                    case 4:
                        eduList.add(new Education(jsonObject));
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Subscribe(sticky = true)
    public void GetState(StateBroadCast msg) {
        if (msg.state == 1) {
            Log.d(TAG, "GetState: 收到服务已启动的通知");
        } else {
            EventBus.getDefault().post(new CommandRequest("http://192.144.239.176:8080/api/android/get_museum_info"));
        }
    }
}
