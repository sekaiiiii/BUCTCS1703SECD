package com.buct.museumguide.ui.News;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.buct.museumguide.R;
import com.buct.museumguide.Service.CommandRequest;
import com.buct.museumguide.Service.NewsResultMsg;
import com.buct.museumguide.Service.StateBroadCast;
import com.buct.museumguide.bean.News;
import com.buct.museumguide.ui.ClassForNews.WebViewer;
import com.buct.museumguide.ui.home.HomeViewModel;
import com.buct.museumguide.util.RequestHelper;
import com.buct.museumguide.util.WebHelper;
import com.youth.banner.Banner;
import com.youth.banner.indicator.CircleIndicator;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/*带参数的直接把新闻url链接拉到这里*/
public class DashboardFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    public static final String TAG = "DashboardFragment";
    private ArrayList<News>newsList=new ArrayList<>();
    private DashboardViewModel dashboardViewModel;
    private Banner banner;
    private NewsRecyclerAdapter adapter;

    private SwipeRefreshLayout mRefreshLayout;
    private int lastItemPosition;  //最后一个item的位置
    private int currentPage = 1;    // 当前请求的页数

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach: ");
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
        banner.stop(); //结束轮播
//        EventBus.getDefault().unregister(this);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
        dashboardViewModel = ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        RecyclerView recyclerView=root.findViewById(R.id.recyclerrView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        mRefreshLayout = root.findViewById(R.id.newsSwipeRefresh);
        //设置显示刷新时的颜色
        mRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        mRefreshLayout.setOnRefreshListener(this);  //添加监听，重写onFresh()方法

        adapter = new NewsRecyclerAdapter();
        recyclerView.setAdapter(adapter);
        Log.d(TAG, "onCreateView: newList.size" + newsList.size());
//        adapter.addDatas(newsList);
        // get and set header
        View header = LayoutInflater.from(getContext()).inflate(R.layout.news_header_banner,recyclerView, false);
        banner = (Banner) header.findViewById(R.id.banner);
        banner.setAdapter(new NewsBannerAdapter(newsList))
                .setIndicator(new CircleIndicator(getContext()))
                .start();
        SearchView searchView = root.findViewById(R.id.searchNews);
        searchView.setOnClickListener(v -> {
            // Toast.makeText(getActivity(),"666",Toast.LENGTH_SHORT).show();
            Navigation.findNavController(v).navigate(R.id.action_navigation_dashboard_to_searchResult);
        });
        adapter.setHeaderView(header);
        getMoreNews(2, -1, "", currentPage++, -1);

        /*try {
            dashboardViewModel.getNews(getContext(), -1, "", 3,10).observe(getViewLifecycleOwner(), new Observer<ArrayList<News>>() {
                @Override
                public void onChanged(@Nullable ArrayList<News> s) {
                    newsList = s;
                    adapter.addDatas(s);
                    banner.setAdapter(new NewsBannerAdapter(s.subList(s.size()-4, s.size())))
                            .setIndicator(new CircleIndicator(getContext()))
                            .start();
                }
            });
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }*/
        Log.d(TAG, "onCreateView: size szie  " + newsList.size());
        adapter.setOnItemClickListener(new NewsRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //MainActivity.url=
                Toast.makeText(getActivity(),adapter.getData().get(position).getUrl(), Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(getActivity(),WebViewer.class);
                intent.putExtra("uri",adapter.getData().get(position).getUrl());
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {
            }
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NotNull RecyclerView recyclerView1, int newState) {
                super.onScrollStateChanged(recyclerView1, newState);
                // Log.d(TAG, "current state==> " + newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    Log.d(TAG, "SCROLL_STATE_IDEL");
                    if (lastItemPosition + 1 == layoutManager.getItemCount()) {
                        getMoreNews(1, -1, "", currentPage++, -1);
                    }
                }
            }

            @Override
            public void onScrolled(@NotNull RecyclerView recyclerView1, int dx, int dy) {
                super.onScrolled(recyclerView1, dx, dy);
                //Log.d(TAG, "onScrolled");
//                mRefreshLayout.setEnabled(recyclerView1.getChildCount() == 0 || recyclerView1.getChildAt(0).getTop() >= 0);
                lastItemPosition = layoutManager.findLastVisibleItemPosition();
            }
        });
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
    }

    @Override
    public void onRefresh() {
        Log.d(TAG, "onRefresh: ");
        mRefreshLayout.setRefreshing(true);
        getMoreNews(0, -1, "", currentPage++, -1);
        mRefreshLayout.setRefreshing(false);
    }

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            Log.d(TAG, "handleMessage: handler收到，" + msg.arg1);
            if(msg.arg1 == 0) {
                adapter.clearAndAddDatas(newsList);
                ArrayList<News> tmpNews = new ArrayList<>(adapter.getData().subList(adapter.getData().size() -4, adapter.getData().size()));
//                // 使用setDatas方法要传入新的对象，不要使用全局变量
                banner.setDatas(tmpNews);
            }
            else if(msg.arg1 == 1)
                adapter.addDatas(newsList);
            else if(msg.arg1 == 2) {
                adapter.clearAndAddDatas(newsList);
                ArrayList<News> tmpNews = new ArrayList<>(adapter.getData().subList(adapter.getData().size() -4, adapter.getData().size()));
//                // 使用setDatas方法要传入新的对象，不要使用全局变量
                banner.setDatas(tmpNews);
            }
            return false;
        }
    });

    public void getMoreNews(int type, int id, String name, int page, int ppn) {
        String url = getActivity().getResources().getString(R.string.get_new_info_url);
        OkHttpClient okHttpClient = WebHelper.getInstance().client;
        HttpUrl.Builder urlBuilder = Objects.requireNonNull(HttpUrl.parse(url)).newBuilder();
        if (id != -1)
            urlBuilder.addQueryParameter("id", String.valueOf(id));
        if (!name.equals(""))
            urlBuilder.addQueryParameter("name", name);
        if (page != -1)
            urlBuilder.addQueryParameter("page", String.valueOf(page));
        if (ppn != -1)
            urlBuilder.addQueryParameter("ppn", String.valueOf(ppn));
        final Request request = new Request.Builder()
                .url(urlBuilder.build().toString())
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d(DashboardFragment.TAG, "onFailure: ", e);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String res = response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(res);
                    String state = String.valueOf(jsonObject.get("status"));
                    if (state.equals("1")) {
                        JSONArray jsonArray = new JSONArray(String.valueOf(jsonObject.getJSONObject("data").get("data")));
                        Log.d(DashboardFragment.TAG, "jsonArray = " + jsonArray.getJSONObject(0));
                        Log.d(DashboardFragment.TAG, "jsonArray.size = " + jsonArray.length());
                        ArrayList<News> tmp_list = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); ++i) {
                            JSONObject tmp_obj = jsonArray.getJSONObject(i);
                            tmp_list.add(new News(tmp_obj));
                        }
                        if(tmp_list.size() > 0) {
                            newsList = tmp_list;
                            Message msg = Message.obtain();
                            msg.arg1 = type;
                            Log.d(TAG, "onResponse: 要传handler");
                            mHandler.sendMessage(msg);
                            Log.d(TAG, "onResponse: 已传handler");
                        }
                        else
                            currentPage = 1;
                    } else {
                        Log.d(DashboardFragment.TAG, "null");
                        currentPage = 1;
                    }
                } catch (JSONException e) {
                    Log.e(DashboardFragment.TAG, "onResponse: ", e);
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}