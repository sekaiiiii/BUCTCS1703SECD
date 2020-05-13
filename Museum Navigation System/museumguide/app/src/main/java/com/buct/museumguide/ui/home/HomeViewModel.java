package com.buct.museumguide.ui.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.buct.museumguide.R;
import com.buct.museumguide.bean.Museum;
import com.buct.museumguide.ui.FragmentForMain.MuseumInfo.MuseumInfo;
import com.buct.museumguide.util.WebHelper;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.annotations.JsonAdapter;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<Museum> museum;

    private void getMuseum(final Context activity, final View view) {
        String url = activity.getResources().getString(R.string.baseUrl) + "/api/android/get_museum_info";
        museum = new MutableLiveData<>();

        OkHttpClient okHttpClient = WebHelper.getInstance().client; //这里最好是写死的，将情况按参数匹配
        final Request request = new Request.Builder()
                .url(url)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.e(HomeFragment.TAG, "onFailure: ",e );
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String responseData = response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(responseData);
                    String state = String.valueOf(jsonObject.get("status"));
                    if(state.equals("1")) {
                        JSONArray jsonArray = new JSONArray(String.valueOf(jsonObject.getJSONObject("data").get("museum_list")));
                        JSONObject firstMuseum = jsonArray.getJSONObject(11);
                        museum.postValue(new Museum(firstMuseum));
                    }
                    else {
                        Log.d(HomeFragment.TAG, "null");
                    }
                } catch (JSONException e) {
                    Log.e(HomeFragment.TAG, "onResponse: ", e);
                    e.printStackTrace();
                }
            }
        });
    }
    public void getState(final Context activity, final View view) {
        getMuseum(activity, view);
    }
    public MutableLiveData<Museum> getFirstMuseum(final Context activity, final View view) {
        return museum;
    }
}