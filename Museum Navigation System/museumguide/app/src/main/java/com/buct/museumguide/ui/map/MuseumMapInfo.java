package com.buct.museumguide.ui.map;

import java.io.Serializable;

import androidx.annotation.NonNull;

public class MuseumMapInfo implements Serializable {
    MuseumMapInfo(double latitude,double logitude,String name){
        setLatitude(latitude);setLogitude(logitude);setTitle(name);
    }
    private double latitude;//维度
    private double logitude;//精度
    private String title;
    private String subtitle;
    private String key;

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLogitude() {
        return logitude;
    }

    public String getKey() {
        return key;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLogitude(double logitude) {
        this.logitude = logitude;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }
}
