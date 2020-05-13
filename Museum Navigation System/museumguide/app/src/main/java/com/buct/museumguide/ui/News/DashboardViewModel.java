package com.buct.museumguide.ui.News;

import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.buct.museumguide.R;
import com.buct.museumguide.bean.News;
import com.buct.museumguide.util.WebHelper;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DashboardViewModel extends ViewModel {

    private MutableLiveData<ArrayList<News>> newsList;
    private void getNews(final Context activity, final View view) {
        String url = activity.getResources().getString(R.string.baseUrl) + "/api/android/get_new_info";
        newsList = new MutableLiveData<>();
        OkHttpClient okHttpClient = WebHelper.getInstance().client; //这里最好是写死的，将情况按参数匹配
        final Request request = new Request.Builder()
                .url(url)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.e(DashboardFragment.TAG, "onFailure: ",e );
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String responseData = response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(responseData);
                    String state = String.valueOf(jsonObject.get("status"));
                    if(state.equals("1")) {
                        JSONArray jsonArray = new JSONArray(String.valueOf(jsonObject.getJSONObject("data").get("data")));
                        Log.d(DashboardFragment.TAG, "jsonArray = " + jsonArray);
                        ArrayList<News> tmp_list = new ArrayList<>();
                        for(int i=0; i < jsonArray.length(); ++i) {
                            JSONObject tmp_obj = jsonArray.getJSONObject(i);
                            tmp_list.add(new News(tmp_obj));
                        }
                        newsList.postValue(tmp_list);
                    }
                    else {
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
    }
    public MutableLiveData<ArrayList<News>> getState(final Context activity, final View view) {
        getNews(activity, view);
        return newsList;
    }
    /*public DashboardViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }*/
}