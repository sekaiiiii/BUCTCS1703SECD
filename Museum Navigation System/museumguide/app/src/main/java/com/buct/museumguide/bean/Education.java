package com.buct.museumguide.bean;

import java.util.ArrayList;

public class Education {
    private int id;
    private String title;
    private String content;
    private String time;
    private String imgUrl;

    public Education(int id, String title, String content, String time, String imgUrl) {
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
    public static ArrayList<Education> getTestData() {
        ArrayList<Education> list = new ArrayList<>();
        list.add( new Education(0, "教育活动0","教育活动内容0", "2020-01-01", "http://img.m.focus.cn/q_70/app/48/4878fa2bfd5a1a93186c7aefd37b01e4.jpg"));
        list.add( new Education(1, "教育活动1","教育活动内容1","2020-01-01", "http://img.mp.itc.cn/upload/20170322/56fee2eed94a493195e3ff98e79d5f50_th.jpeg"));
        list.add( new Education(2, "教育活动2","教育活动内容2","2020-01-01", "http://img.m.focus.cn/q_70/app/48/4878fa2bfd5a1a93186c7aefd37b01e4.jpg"));
        list.add( new Education(3, "教育活动3","教育活动内容3","2020-01-01", "http://img.mp.itc.cn/upload/20170322/56fee2eed94a493195e3ff98e79d5f50_th.jpeg"));
        list.add( new Education(4, "教育活动4","教育活动内容4","2020-01-01", "http://img.m.focus.cn/q_70/app/48/4878fa2bfd5a1a93186c7aefd37b01e4.jpg"));
        return list;
    }
}
