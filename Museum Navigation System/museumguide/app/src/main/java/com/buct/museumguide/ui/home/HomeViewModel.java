package com.buct.museumguide.ui.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.buct.museumguide.R;
import com.buct.museumguide.Service.CollectionMsg;
import com.buct.museumguide.Service.CollectionResultMsg;
import com.buct.museumguide.Service.CommandRequest;
import com.buct.museumguide.Service.EducationMsg;
import com.buct.museumguide.Service.ExhibitionMsg;
import com.buct.museumguide.Service.MuseumInfoMsg;
import com.buct.museumguide.Service.NewsMsg;
import com.buct.museumguide.bean.Museum;
import com.buct.museumguide.ui.FragmentForMain.CommonList.CommonList;
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
import java.util.Objects;
import java.util.concurrent.RecursiveTask;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HomeViewModel extends ViewModel {
    private MutableLiveData<Museum> mMuseumLivaData = new MutableLiveData<>();
    public LiveData<Museum> getMuseumLivaData() {
        if(mMuseumLivaData == null) {
            mMuseumLivaData = new MutableLiveData<>();
        }
        return mMuseumLivaData;
    }
    public void setMuseumLivaData(Museum museum) {
        if(mMuseumLivaData!=null) {
            mMuseumLivaData.setValue(museum);
        }
    }
    @Override
    protected void onCleared() {
        super.onCleared();
        mMuseumLivaData=null;
    }
}