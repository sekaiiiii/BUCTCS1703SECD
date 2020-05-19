package com.buct.museumguide.Service;
/*
* 服务指令类，服务发送String类型消息使用此类型
* */
public class CommandRequest {
    public final String url;

    public CommandRequest(String url) {
        this.url = url;
    }
}
