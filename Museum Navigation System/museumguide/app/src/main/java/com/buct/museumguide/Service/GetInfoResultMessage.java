package com.buct.museumguide.Service;

/*
 * 获取信息消息事件的结果
 * */
public class GetInfoResultMessage {
    /*
        type = 1 一个博物馆信息（HomeViewModel，getOneMuseumInfo中发送）
        type = 2 博物馆列表
        type = 2 展览列表
        type = 3 藏品列表
        type = 4 新闻列表
        type = 5 教育活动
        ...(后续补充)
    */
    public int type;
    public String res;
    public GetInfoResultMessage(int type, String res) {
        this.type = type;
        this.res = res;
    }
}
