package com.buct.museumguide.util;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/*
* 接口回调
* */
public class WebHelper {
    //必须在子线程运行，非子线程运行会报错
    public String Get(String url)throws IOException {
        OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            try (Response response = client.newCall(request).execute()) {
                return response.body().string();
            }
    }
}
