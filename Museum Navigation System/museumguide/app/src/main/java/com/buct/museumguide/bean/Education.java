package com.buct.museumguide.bean;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Education {
    private int id;
    private String name;
    private String start_time;
    private String end_time;
    private String time;
    private int tag;
    private String content;
    private String url;
    private String cooperator;
    private int museum_id;
    private JSONArray image_list;
    private String imgUrl;

    /*public Education(int id, String name, String content, String time, String imgUrl) {
        this.id = id;
        this.name = name;
        this.content = content;
        this.time = time;
        this.imgUrl = imgUrl;
    }*/

    public Education(JSONObject object) throws JSONException {
        this.id = object.optInt("id",-1);
        this.name = object.optString("name","");
        this.start_time = object.optString("start_time","");
        this.end_time = object.optString("end_time","");
        this.time = object.optString("time","");
        this.tag = object.optInt("tag",-1);
        this.content = object.optString("content","");
        this.url = object.optString("url","");
        this.cooperator = object.optString("cooperator","");
        this.museum_id = object.optInt("museum_id",-1);
        this.image_list = object.getJSONArray("image_list");
        this.imgUrl = "http://img.m.focus.cn/q_70/app/48/4878fa2bfd5a1a93186c7aefd37b01e4.jpg";
    }
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
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
    public String getContent() {
        return content;
    }
    public String getUrl() {
        return url;
    }
    public String getCooperator() {
        return cooperator;
    }
    public int getMuseum_id() {
        return museum_id;
    }
    public JSONArray getImage_list() {
        return image_list;
    }
    public String getImgUrl() {
        return imgUrl;
    }

    /*public static ArrayList<Education> getTestData() {
        ArrayList<Education> list = new ArrayList<>();
        list.add( new Education(0, "教育活动0","教育活动内容0", "2020-01-01", "http://img.m.focus.cn/q_70/app/48/4878fa2bfd5a1a93186c7aefd37b01e4.jpg"));
        list.add( new Education(1, "教育活动1","教育活动内容1","2020-01-01", "http://img.mp.itc.cn/upload/20170322/56fee2eed94a493195e3ff98e79d5f50_th.jpeg"));
        list.add( new Education(2, "教育活动2","教育活动内容2","2020-01-01", "http://img.m.focus.cn/q_70/app/48/4878fa2bfd5a1a93186c7aefd37b01e4.jpg"));
        list.add( new Education(3, "教育活动3","教育活动内容3","2020-01-01", "http://img.mp.itc.cn/upload/20170322/56fee2eed94a493195e3ff98e79d5f50_th.jpeg"));
        list.add( new Education(4, "教育活动4","教育活动内容4","2020-01-01", "http://img.m.focus.cn/q_70/app/48/4878fa2bfd5a1a93186c7aefd37b01e4.jpg"));
        return list;
    }*/
    public Education() {
        this.name = "教育1";
        this.content = "教育内容1";
        this.imgUrl = "http://img.m.focus.cn/q_70/app/48/4878fa2bfd5a1a93186c7aefd37b01e4.jpg";
    }
    public static Education getTestData() {
        return new Education();
    }
}
