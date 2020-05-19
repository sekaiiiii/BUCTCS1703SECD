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
import androidx.lifecycle.MutableLiveData;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

/*带参数的直接把新闻url链接拉到这里*/
public class DashboardFragment extends Fragment {
    public static final String TAG = "DashboardFragment";
    private ArrayList<News>newsList=new ArrayList<>();
    private DashboardViewModel dashboardViewModel;
    private Banner banner;
    private NewsRecyclerAdapter adapter;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach: ");
    }

    @Override
    public void onStart() {
        super.onStart();
        banner.start(); //开始轮播
    }

    @Override
    public void onStop() {
        super.onStop();
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
//        adapter.addDatas(newsList);
        // get and set header
        View header = LayoutInflater.from(getContext()).inflate(R.layout.news_header_banner,recyclerView, false);
        banner = (Banner) header.findViewById(R.id.banner);
        SearchView searchView = root.findViewById(R.id.searchNews);
        searchView.setOnClickListener(v -> {
            // Toast.makeText(getActivity(),"666",Toast.LENGTH_SHORT).show();
            Navigation.findNavController(v).navigate(R.id.action_navigation_dashboard_to_searchResult);
        });
        adapter.setHeaderView(header);

        try {
            dashboardViewModel.getNews(getContext(), -1, "", -1,-1).observe(getViewLifecycleOwner(), new Observer<ArrayList<News>>() {
                @Override
                public void onChanged(@Nullable ArrayList<News> s) {
                    newsList = s;
                    adapter.addDatas(s);
                    banner.setAdapter(new NewsBannerAdapter(s.subList(s.size()-4, s.size())))
                            .setIndicator(new CircleIndicator(getContext()))
                            .start();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "onCreateView: size szie  " + newsList.size());
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

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
    }
}