package com.buct.museumguide.ui.User;

import android.util.Log;

import com.buct.museumguide.ui.FragmentForUsers.Login.Login;
import com.buct.museumguide.util.WebHelper;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NotificationsViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private MutableLiveData<Integer> state;
    public NotificationsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");
    }
    public LiveData<Integer> logout(String cookie){
        //网络请求
        state=new MutableLiveData<>();
        OkHttpClient client= WebHelper.getInstance().client;
        Request request = new Request.Builder()
                .url("http://192.144.239.176:8080/api/android/logout")
                .header("Cookie", cookie)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.e(Login.TAG, "onFailure: ",e );
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
               // System.out.println(cookie+" "+response.body().string());
                state.postValue(1);
            }
        });
        return state;
    }
    public LiveData<String> getText() {
        return mText;
    }
}