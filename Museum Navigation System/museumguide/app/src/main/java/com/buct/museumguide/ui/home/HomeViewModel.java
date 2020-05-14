package com.buct.museumguide.ui.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.buct.museumguide.R;
import com.buct.museumguide.Service.CommandRequest;
import com.buct.museumguide.Service.GetInfoMessage;
import com.buct.museumguide.bean.Museum;
import com.buct.museumguide.ui.FragmentForMain.MuseumInfo.MuseumInfo;
import com.buct.museumguide.util.WebHelper;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.annotations.JsonAdapter;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HomeViewModel extends ViewModel {

    public void getOneMuseumInfo(final Context activity, String museumName) {
        String url = activity.getResources().getString(R.string.get_museum_info_url);
        HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
        if(museumName.equals(""))
            urlBuilder.addQueryParameter("name", "中国农业博物馆");
        else
            urlBuilder.addQueryParameter("name", museumName);
        EventBus.getDefault().post(new GetInfoMessage(1, urlBuilder.build().toString()));
    }
    public void getExhibitionList(final Context activity) {
        String url = activity.getResources().getString(R.string.get_exhibition_info_url);
    }
}