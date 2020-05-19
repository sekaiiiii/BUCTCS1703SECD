package com.buct.museumguide.bean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Museum {
    private int id;
    private String name;
    private String establishment_time;
    private String open_time;
    private String close_time;
    private String time;
    private String introduction;
    private String visit_info;
    private String attention;
    private String exhibition_score;
    private String environment_score;
    private String service_score;
    private String position_name;
    private String longitude;
    private String latitude;
    private JSONArray image_list;

//    public Museum(int id, String name, String establishment_time, String open_time, String close_time,String time, String introduction, String visit_info, String )
    public Museum(JSONObject object) throws JSONException {
        this.id = object.optInt("id", -1);
        this.name = object.optString("name", "");
        this.establishment_time = object.optString("establishment_time", "");
        this.open_time = object.optString("open_time", "");
        this.close_time = object.optString("close_time", "");
        this.time = object.optString("time", "");
        this.introduction = object.optString("introduction", "");
        this.visit_info = object.optString("visit_info", "");
        this.attention = object.optString("attention", "");
        this.exhibition_score = object.optString("exhibition_score", "");
        this.environment_score = object.optString("environment_score", "");
        this.service_score = object.optString("service_score", "");
        this.position_name = object.optString("position_name", "");
        this.longitude = object.optString("longitude", "");
        this.latitude = object.optString("latitude", "");
        this.image_list = object.getJSONArray("image_list");
    }
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getEstablishment_time() {
        return establishment_time;
    }
    public String getOpen_time() {
        return open_time;
    }
    public String getClose_time() {
        return close_time;
    }
    public String getTime() {
        return time;
    }
    public String getIntroduction() {
        return introduction;
    }
    public String getVisit_info() {
        return visit_info;
    }
    public String getAttention() {
        return attention;
    }
    public String getExhibition_score() {
        return exhibition_score;
    }
    public String getEnvironment_score() {
        return environment_score;
    }
    public String getService_score() {
        return service_score;
    }
    public String getPosition_name() {
        return position_name;
    }
    public String getLongitude() {
        return longitude;
    }
    public String getLatitude() {
        return latitude;
    }
    public JSONArray getImage_list() { return image_list;}
}
