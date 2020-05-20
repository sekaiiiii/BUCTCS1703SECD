package com.buct.museumguide.ui.FragmentForMain.MuseumList;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.buct.museumguide.R;
import com.buct.museumguide.bean.Museum;
import com.buct.museumguide.util.WebHelper;

import org.greenrobot.eventbus.EventBus;
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

public class MuseumListViewModel extends ViewModel {
    private MutableLiveData<ArrayList<com.buct.museumguide.bean.Museum>> liveData;
    public MutableLiveData<ArrayList<com.buct.museumguide.bean.Museum>> getMuseums(final Context activity) throws IOException, JSONException {
        liveData = new MutableLiveData<>();
        String url = "http://192.144.239.176:8080/api/android/get_museum_info?order_by=0";
        OkHttpClient okHttpClient = WebHelper.getInstance().client;
        HttpUrl.Builder urlBuilder = Objects.requireNonNull(HttpUrl.parse(url)).newBuilder();
        final Request request = new Request.Builder().url(urlBuilder.build().toString()).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d(DefaultFragment.TAG,"onFailure",e);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String res = response.body().string();
                Log.d(DefaultFragment.TAG,"onResponse: res= " +res);
                try {
                    JSONObject jsonObject = new JSONObject(res);
                    String state = String.valueOf(jsonObject.get("status"));
                    if(state.equals("1")){
                        JSONArray jsonArray = new JSONArray(String.valueOf(jsonObject.getJSONObject("data").get("museum_list")));
                        Log.d(DefaultFragment.TAG, "jsonArray = " + jsonArray);
                        ArrayList<com.buct.museumguide.bean.Museum> temp = new ArrayList<>();
                        for (int i=0; i< jsonArray.length(); ++i){
                            JSONObject temp_obj = jsonArray.getJSONObject(i);
                            temp.add(new Museum(temp_obj));
                        }
                        liveData.postValue(temp);
                    }
                    else {
                        Log.d(DefaultFragment.TAG, "null");
                    }
                } catch (JSONException e){
                    Log.e(DefaultFragment.TAG,"onResponse: ", e);
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return  liveData;
    }
}
