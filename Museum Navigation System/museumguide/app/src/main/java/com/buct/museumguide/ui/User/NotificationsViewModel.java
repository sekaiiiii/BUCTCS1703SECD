package com.buct.museumguide.ui.User;

import android.util.Log;

import com.buct.museumguide.Service.loginstatemessage;
import com.buct.museumguide.bean.PostResultMessage;
import com.buct.museumguide.bean.WebRequestMessage;
import com.buct.museumguide.ui.FragmentForUsers.Login.Login;
import com.buct.museumguide.util.WebHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
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
        EventBus.getDefault().register(this);
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");
    }
    public LiveData<Integer> logout(String cookie){
        //网络请求
        EventBus.getDefault().post(new WebRequestMessage("http://192.144.239.176:8080/api/android/logout",100,cookie,null));
        state=new MutableLiveData<>();
        return state;
}
    public LiveData<String> getText() {
        return mText;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        EventBus.getDefault().unregister(this);
    }
    @Subscribe
    public void getpostres(loginstatemessage msg){
        System.out.println(msg.res);
        state.postValue(1);
    }
}