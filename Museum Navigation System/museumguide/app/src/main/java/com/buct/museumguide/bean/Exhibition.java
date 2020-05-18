package com.buct.museumguide.bean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Exhibition {
    private int id;
    private String name;
    private String content;
    private String start_time;
    private String end_time;
    private String time;
    private int tag;
    private JSONArray image_list;
    private String imgUrl;

    public Exhibition(int id, String name, String content, String time, String imgUrl) {
        this.id = id;
        this.name = name;
        this.content = content;
        this.time = time;
        this.imgUrl = imgUrl;
    }
    public Exhibition(JSONObject object) throws JSONException {
        this.id = object.optInt("id", -1);
        this.name = object.optString("name", "");
        this.content = object.optString("content", "");
        this.start_time = object.optString("start_time", "");
        this.end_time = object.optString("end_time", "");
        this.time = object.optString("time", "");
        this.tag = object.optInt("tag", -1);
        this.image_list = object.getJSONArray("image_list");
        this.imgUrl = "http://img.mp.itc.cn/upload/20170322/56fee2eed94a493195e3ff98e79d5f50_th.jpeg";
    }

    public Exhibition() {
        this.id = 1;
        this.name = "展览1";
        this.content = "内容1";
        this.time = "时间1";
        this.imgUrl = "http://img.mp.itc.cn/upload/20170322/56fee2eed94a493195e3ff98e79d5f50_th.jpeg";
    }

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getContent() {
        return content;
    }
    public String getStart_time() {
        return start_time;
    }
    public String getEnd_time() {
        return end_time;
    }
    public String getTime() {
        return time;
    }
    public int getTag() {
        return tag;
    }
    public JSONArray getImage_list() {
        return image_list;
    }
    public String getImgUrl() {
        return imgUrl;
    }

    /*public static ArrayList<Exhibition> getTestData() {
        ArrayList<Exhibition> list = new ArrayList<>();
        for(int i = 0; i < 10; ++i) {
            list.add(new Exhibition(i, "展览0", "展览内容0", "2020-01-01--2020-02-02", "http://img.m.focus.cn/q_70/app/48/4878fa2bfd5a1a93186c7aefd37b01e4.jpg", "中国国家博物馆"));
            list.add(new Exhibition(i, "展览1", "展览内容1", "2020-01-01--2020-02-02", "http://img.mp.itc.cn/upload/20170322/56fee2eed94a493195e3ff98e79d5f50_th.jpeg","中国国家博物馆"));
            list.add(new Exhibition(i, "展览2", "展览内容2", "2020-01-01--2020-02-02", "http://img.m.focus.cn/q_70/app/48/4878fa2bfd5a1a93186c7aefd37b01e4.jpg", "中国国家博物馆"));
            list.add(new Exhibition(i, "展览3", "展览内容3", "2020-01-01--2020-02-02", "http://img.mp.itc.cn/upload/20170322/56fee2eed94a493195e3ff98e79d5f50_th.jpeg", "中国国家博物馆"));
            list.add(new Exhibition(i, "展览4", "展览内容4", "2020-01-01--2020-02-02", "http://img.m.focus.cn/q_70/app/48/4878fa2bfd5a1a93186c7aefd37b01e4.jpg", "中国国家博物馆"));
        }
        return list;
    }*/
    public static Exhibition getTestData() {
        return new Exhibition();
    }
}
