package com.buct.museumguide.bean;

import java.util.ArrayList;

public class Collection {
    private int id;
    private String title;
    private String content;
    private String time;
    private String imgUrl;

    public Collection(int id, String title, String content, String time, String imgUrl) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.time = time;
        this.imgUrl = imgUrl;
    }
    public String getTitle() {
        return title;
    }
    public String getTime() {
        return time;
    }
    public String getImgUrl() {
        return imgUrl;
    }
    public String getContent() {
        return content;
    }
    public static ArrayList<Collection> getTestData() {
        ArrayList<Collection> list = new ArrayList<>();
        for(int i=0;i < 10;++i) {
            list.add(new Collection(0, "藏品0", "藏品活动内容0", "2020-01-01", "http://img.m.focus.cn/q_70/app/48/4878fa2bfd5a1a93186c7aefd37b01e4.jpg"));
            list.add(new Collection(1, "藏品1", "藏品活动内容1", "2020-01-01", "http://img.mp.itc.cn/upload/20170322/56fee2eed94a493195e3ff98e79d5f50_th.jpeg"));
            list.add(new Collection(2, "藏品2", "藏品活动内容2", "2020-01-01", "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=2147061954,2354234137&fm=26&gp=0.jpg"));
            list.add(new Collection(3, "藏品3", "藏品活动内容3", "2020-01-01", "http://img.mp.itc.cn/upload/20170322/56fee2eed94a493195e3ff98e79d5f50_th.jpeg"));
            list.add(new Collection(4, "藏品4", "藏品活动内容4", "2020-01-01", "http://img.m.focus.cn/q_70/app/48/4878fa2bfd5a1a93186c7aefd37b01e4.jpg"));
        }
        return list;
    }
}