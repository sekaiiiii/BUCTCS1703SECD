package com.buct.museumguide.Service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;

import com.buct.museumguide.util.WebHelper;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import androidx.annotation.Nullable;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class GetUploadService extends Service {
    private String cookie;
    private String res;
    static AudioServiceCallBack ServiceCallBack;
    public void setCallBack(AudioServiceCallBack serviceCallBack) {
        this.ServiceCallBack = serviceCallBack;
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        System.out.println("被绑定");
        return null;
    }

    @Override
    public void onCreate() {
        System.out.println("服务启动");

        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("被调用");


        return super.onStartCommand(intent, flags, startId);

    }

}
