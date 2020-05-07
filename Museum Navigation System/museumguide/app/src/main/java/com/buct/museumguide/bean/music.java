package com.buct.museumguide.bean;

import androidx.annotation.NonNull;

public class music {
    private String title;
    private String url;
    private String author;
    private String subtile;
    private String describe;
    private String MetaID;
    private String Type;
    private Long duration;
    public String getAuthor() {
        return author;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    @NonNull
    @Override
    public String toString() {
        return title+url+author+subtile+describe+MetaID+Type;
    }

    public String getDescribe() {
        return describe;
    }

    public String getMetaID() {
        return MetaID;
    }

    public String getSubtile() {
        return subtile;
    }

    public String getTitle() {
        return title;
    }

    public String getType() {
        return Type;
    }

    public String getUrl() {
        return url;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public void setMetaID(String metaID) {
        MetaID = metaID;
    }

    public void setSubtile(String subtile) {
        this.subtile = subtile;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setType(String type) {
        Type = type;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
