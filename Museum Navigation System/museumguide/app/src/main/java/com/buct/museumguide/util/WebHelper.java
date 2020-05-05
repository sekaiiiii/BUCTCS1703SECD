package com.buct.museumguide.util;

import android.os.Build;

import java.io.IOException;

import androidx.annotation.RequiresApi;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/*
* 接口回调,调用方法OkHttpClient client= WebHelper.getInstance().client;
*  别新建线程了，全部回调
* */
public class WebHelper {
    //必须在子线程运行，非子线程运行会报错
    private static WebHelper webHelper;
    private WebHelper(){}
    public OkHttpClient client = new OkHttpClient();
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public String Get(String url)throws IOException {
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            try (Response response = client.newCall(request).execute()) {
                return response.body().string();
            }
    }
    public static synchronized WebHelper getInstance(){
        if(webHelper==null)webHelper=new WebHelper();
        return webHelper;
    }
    //post类封装？
}
