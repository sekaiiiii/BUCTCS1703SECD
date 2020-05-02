package com.buct.museumguide.bean;

import java.util.ArrayList;

public class Exhibition {
    private int id;
    private String title;
    private String content;
    private String time;
    private String imgUrl;
    private String museumName;

    public Exhibition(int id, String title, String content, String time, String imgUrl, String museumName) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.time = time;
        this.imgUrl = imgUrl;
        this.museumName = museumName;
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
    public String getMuseumName() {
        return museumName;
    }
    public static ArrayList<Exhibition> getTestData() {
        ArrayList<Exhibition> list = new ArrayList<>();
        for(int i = 0; i < 10; ++i) {
            list.add(new Exhibition(i, "展览0", "展览内容0", "2020-01-01--2020-02-02", "http://img.m.focus.cn/q_70/app/48/4878fa2bfd5a1a93186c7aefd37b01e4.jpg", "中国国家博物馆"));
            list.add(new Exhibition(i, "展览1", "展览内容1", "2020-01-01--2020-02-02", "http://img.mp.itc.cn/upload/20170322/56fee2eed94a493195e3ff98e79d5f50_th.jpeg","中国国家博物馆"));
            list.add(new Exhibition(i, "展览2", "展览内容2", "2020-01-01--2020-02-02", "http://img.m.focus.cn/q_70/app/48/4878fa2bfd5a1a93186c7aefd37b01e4.jpg", "中国国家博物馆"));
            list.add(new Exhibition(i, "展览3", "展览内容3", "2020-01-01--2020-02-02", "http://img.mp.itc.cn/upload/20170322/56fee2eed94a493195e3ff98e79d5f50_th.jpeg", "中国国家博物馆"));
            list.add(new Exhibition(i, "展览4", "展览内容4", "2020-01-01--2020-02-02", "http://img.m.focus.cn/q_70/app/48/4878fa2bfd5a1a93186c7aefd37b01e4.jpg", "中国国家博物馆"));
        }
        return list;
    }
}
