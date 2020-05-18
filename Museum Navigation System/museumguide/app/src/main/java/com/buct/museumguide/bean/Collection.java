package com.buct.museumguide.bean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Collection {
    private int id;
    private int museum_id;
    private String name;
    private String content;
    private String material;
    private int tag;
    private JSONArray image_list;
    private String img_url;  //删

    public Collection(int id, int museum_id, String name, String content, String material, int tag, JSONArray image_list, String img_url) {
        this.id = id;
        this.museum_id = museum_id;
        this.name = name;
        this.content = content;
        this.material = material;
        this.tag = tag;
        this.image_list = image_list;
        this.img_url = img_url;
    }
    public int getId() {
        return id;
    }
    public int getMuseum_id() {
        return museum_id;
    }
    public String getName() {return name;}
    public String getContent() {
        return content;
    }
    public String getMaterial() {
        return material;
    }
    public int getTag() {
        return tag;
    }
    public JSONArray getImage_list() {
        return image_list;
    }
    public String getImgUrl() {
        return img_url;
    }

    /*public static ArrayList<Collection> getTestData() {
        ArrayList<Collection> list = new ArrayList<>();
        for(int i=0;i < 10;++i) {
            list.add(new Collection(0, "藏品0", "藏品活动内容0", "2020-01-01", "http://img.m.focus.cn/q_70/app/48/4878fa2bfd5a1a93186c7aefd37b01e4.jpg"));
            list.add(new Collection(1, "藏品1", "藏品活动内容1", "2020-01-01", "http://img.mp.itc.cn/upload/20170322/56fee2eed94a493195e3ff98e79d5f50_th.jpeg"));
            list.add(new Collection(2, "藏品2", "藏品活动内容2", "2020-01-01", "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=2147061954,2354234137&fm=26&gp=0.jpg"));
            list.add(new Collection(3, "藏品3", "藏品活动内容3", "2020-01-01", "http://img.mp.itc.cn/upload/20170322/56fee2eed94a493195e3ff98e79d5f50_th.jpeg"));
            list.add(new Collection(4, "藏品4", "藏品活动内容4", "2020-01-01", "http://img.m.focus.cn/q_70/app/48/4878fa2bfd5a1a93186c7aefd37b01e4.jpg"));
        }
        return list;
    }*/
    public Collection(JSONObject object) throws JSONException {
        this.id = object.optInt("id", -1);
        this.museum_id = object.optInt("museum_id", -1);
        this.name = object.optString("name", "");
        this.content = object.optString("content", "");
        this.material = object.optString("material", "");
        this.tag = object.optInt("tag", -1);
        this.image_list = object.getJSONArray("image_list");
        this.img_url = "http://img.m.focus.cn/q_70/app/48/4878fa2bfd5a1a93186c7aefd37b01e4.jpg";
    }
    public Collection() {
        this.name = "藏品1";
        this.img_url = "http://img.m.focus.cn/q_70/app/48/4878fa2bfd5a1a93186c7aefd37b01e4.jpg";
    }
    public static Collection getTestData() {
        return new Collection();
    }
}