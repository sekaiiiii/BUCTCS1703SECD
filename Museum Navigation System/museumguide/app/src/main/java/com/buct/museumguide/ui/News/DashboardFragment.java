package com.buct.museumguide.ui.News;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.EventLog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.buct.museumguide.R;
import com.buct.museumguide.Service.CommandRequest;
import com.buct.museumguide.Service.NewsResultMsg;
import com.buct.museumguide.Service.StateBroadCast;
import com.buct.museumguide.bean.News;
import com.buct.museumguide.ui.ClassForNews.WebViewer;
import com.buct.museumguide.ui.home.HomeViewModel;
import com.buct.museumguide.util.RequestHelper;
import com.youth.banner.Banner;
import com.youth.banner.indicator.CircleIndicator;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

/*带参数的直接把新闻url链接拉到这里*/
public class DashboardFragment extends Fragment {
    public static final String TAG = "DashboardFragment";
    private ArrayList<News>newsList=new ArrayList<>();
    private DashboardViewModel dashboardViewModel;
    private Banner banner;
    private NewsRecyclerAdapter adapter;
    private RequestHelper requestHelper = new RequestHelper();

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach: ");
        if(!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
    }

    @Override
    public void onStart() {
        super.onStart();
//        EventBus.getDefault().register(this);
        banner.start(); //开始轮播
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
        banner.stop(); //结束轮播
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        RecyclerView recyclerView=root.findViewById(R.id.recyclerrView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        adapter = new NewsRecyclerAdapter();
        recyclerView.setAdapter(adapter);
        adapter.addDatas(newsList);
        // get and set header
        View header = LayoutInflater.from(getContext()).inflate(R.layout.news_header_banner,recyclerView, false);
        banner = (Banner) header.findViewById(R.id.banner);
        SearchView searchView = header.findViewById(R.id.searchNews);
        searchView.setOnClickListener(v -> {
            // Toast.makeText(getActivity(),"666",Toast.LENGTH_SHORT).show();
            Navigation.findNavController(v).navigate(R.id.action_navigation_dashboard_to_searchResult);
        });
        adapter.setHeaderView(header);

        banner.setAdapter(new NewsBannerAdapter(newsList))
                .setIndicator(new CircleIndicator(getContext()))
                .start();

        adapter.setOnItemClickListener(new NewsRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //MainActivity.url=
                Toast.makeText(getActivity(),newsList.get(position).getUrl(), Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(getActivity(),WebViewer.class);
                intent.putExtra("uri",newsList.get(position).getUrl());
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {
            }
        });
        return root;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Subscribe(sticky = true)
    public void GetState(StateBroadCast msg) {
        if (msg.state == 1) {
            System.out.println("收到了服务已启动的通知");
            requestHelper.getNews(Objects.requireNonNull(getActivity()), -1, "");
        } else {
            EventBus.getDefault()
                    .post(new
                            CommandRequest
                            ("http://192.144.239.176:8080/api/android/get_new_info"));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
        requestHelper.getNews(Objects.requireNonNull(getActivity()), -1, "");
    }

    @Subscribe(sticky = true)
    public void onReceiveNews(NewsResultMsg newsResultMsg) {
        String responseData = newsResultMsg.res;
        Log.d(TAG, "onReceiveNews: " + responseData);
        try {
            JSONObject jsonObject = new JSONObject(responseData);
            String state = String.valueOf(jsonObject.get("status"));
            parseJSONWithJSONObject(String.valueOf(jsonObject.getJSONObject("data").get("data")));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void parseJSONWithJSONObject(String jsonData) throws JSONException {
        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            for(int i=0;i<jsonArray.length();++i) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                newsList.add(new News(jsonObject));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
