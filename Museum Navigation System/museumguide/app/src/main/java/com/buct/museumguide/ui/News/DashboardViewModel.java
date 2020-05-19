package com.buct.museumguide.ui.News;

import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.buct.museumguide.R;
import com.buct.museumguide.Service.NewsMsg;
import com.buct.museumguide.bean.News;
import com.buct.museumguide.util.WebHelper;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DashboardViewModel extends ViewModel {
    private MutableLiveData<ArrayList<News>> liveData;

    public MutableLiveData<ArrayList<News>> getNews(final Context activity, int id, String name, int page, int ppn) throws IOException, JSONException {
        liveData = new MutableLiveData<>();
        String url = activity.getResources().getString(R.string.get_new_info_url);
//        OkHttpClient okHttpClient = WebHelper.getInstance().client;
        OkHttpClient okHttpClient = new OkHttpClient();
        HttpUrl.Builder urlBuilder = Objects.requireNonNull(HttpUrl.parse(url)).newBuilder();
        if (id != -1)
            urlBuilder.addQueryParameter("id", String.valueOf(id));
        if (!name.equals(""))
            urlBuilder.addQueryParameter("name", name);
        if (page != -1)
            urlBuilder.addQueryParameter("page", String.valueOf(page));
        if (ppn != -1)
            urlBuilder.addQueryParameter("ppn", String.valueOf(ppn));
//        Log.d(DashboardFragment.TAG, "url: " + urlBuilder.build().toString());
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
//                Log.d(DashboardFragment.TAG, "onResponse: res = " + res);
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
                        liveData.postValue(tmp_list);
                    } else {
                        Log.d(DashboardFragment.TAG, "null");
                    }
                } catch (JSONException e) {
                    Log.e(DashboardFragment.TAG, "onResponse: ", e);
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return liveData;
    }

    private ArrayList<News> parseJSONWithJSONObject(String jsonData) throws JSONException {
        ArrayList<News> newsList = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            for (int i = 0; i < jsonArray.length(); ++i) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                newsList.add(new News(jsonObject));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newsList;
    }
}