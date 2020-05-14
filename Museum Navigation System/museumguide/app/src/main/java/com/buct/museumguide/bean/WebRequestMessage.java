package com.buct.museumguide.bean;

import okhttp3.RequestBody;

public class WebRequestMessage {
    public final String url;
    public final int requestcode;
    public final String cookie;
    public final RequestBody body;
    // 100为带cookie的get请求，200为post请求，300为带cookie的post请求，400为普通的get请求
    public WebRequestMessage(String url, int requestcode, String cookie, RequestBody body) {
        this.url = url;
        this.requestcode = requestcode;
        this.cookie = cookie;
        this.body = body;
    }
}
