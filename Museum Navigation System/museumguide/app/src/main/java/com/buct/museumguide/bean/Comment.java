package com.buct.museumguide.bean;

import org.json.JSONObject;

public class Comment {
    private int id;
    private String time;
    private String content;
    private double exhibition_score;
    private double environment_score;
    private double service_score;
    private String name;
    private String mail_address;

    public Comment(JSONObject object) {
        this.id = object.optInt("id", -1);
        this.time = object.optString("time", "");
        this.content = object.optString("content", "");
        this.exhibition_score = object.optDouble("exhibition_score", 0.0);
        this.environment_score = object.optDouble("environment_score", 0.0);
        this.service_score = object.optDouble("service_score", 0.0);
        this.name = object.optString("name", "");
        this.mail_address = object.optString("mail_address", "");
    }
    public int getId() {
        return id;
    }
    public String getTime() {
        return time;
    }
    public String getContent() {
        return content;
    }
    public double getExhibition_score() {
        return exhibition_score;
    }
    public double getEnvironment_score() {
        return environment_score;
    }
    public double getService_score() {
        return service_score;
    }
    public String getName() {
        return name;
    }
    public String getMail_address() {
        return mail_address;
    }
}
