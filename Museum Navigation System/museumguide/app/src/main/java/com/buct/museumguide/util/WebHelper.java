package com.buct.museumguide.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;

import org.json.JSONObject;

import java.io.IOException;

import androidx.annotation.RequiresApi;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/*
* 接口回调,调用方法OkHttpClient client= WebHelper.getInstance().client;
*  别新建线程了，全部回调
* */
public class WebHelper{
    //必须在子线程运行，非子线程运行会报错
    public static MediaType jsonmediaType = MediaType.parse("application/json");
    private static WebHelper webHelper;
    private WebHelper(){}
    public OkHttpClient client = new OkHttpClient();
    public static synchronized WebHelper getInstance(){
        if(webHelper==null)webHelper=new WebHelper();
        return webHelper;
    }
    public static String getInfo(String url) throws IOException {
        Request request=new Request.Builder().url(url).build();
        Response response=WebHelper.getInstance().client.newCall(request).execute();
        return response.body().string();
    }
    public static String getInfoWithCookie(String url,String cookie) throws IOException {
        Request request=new Request.Builder().url(url).header("Cookie",cookie).build();
        Response response=WebHelper.getInstance().client.newCall(request).execute();
        return response.body().string();
    }
    public static String postInfo(String url, RequestBody body) throws  IOException{
        Request request=new Request.Builder().url(url).post(body).build();
        Response response=WebHelper.getInstance().client.newCall(request).execute();
        return response.body().string();
    }
    public static String postWithCookie(String url, RequestBody body,String cookie) throws IOException {
        Request request=new Request.Builder().url(url).header("Cookie",cookie).post(body).build();
        Response response=WebHelper.getInstance().client.newCall(request).execute();
        return response.body().string();
    }
    public static RequestBody SetJSonBody(JSONObject object){
        return RequestBody.create(jsonmediaType,object.toString());
    }
    public static String getCookie(Context context){
        SharedPreferences Infos = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        return Infos.getString("cookie","");
    }
    public static void setCookie(Context context,String cookie){
        SharedPreferences Infos = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        Infos.edit().putString("cookie",cookie).apply();
    }
}
