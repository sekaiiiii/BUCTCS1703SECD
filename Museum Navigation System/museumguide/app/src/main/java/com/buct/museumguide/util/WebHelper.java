package com.buct.museumguide.util;

import android.os.AsyncTask;
import android.os.Build;

import java.io.IOException;

import androidx.annotation.RequiresApi;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
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
    //AsyncTask<String ,String,String>
    //post类封装？
    public static class WebGet extends AsyncTask<String, Integer, String> {
        private OkHttpClient client;
        /*预先获取网络请求*/
        @Override
        protected void onPreExecute() {
            this.client=WebHelper.getInstance().client;
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(String... strings) {

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }


        @Override
        protected void onCancelled() {
            super.onCancelled();
        }
    }
}
