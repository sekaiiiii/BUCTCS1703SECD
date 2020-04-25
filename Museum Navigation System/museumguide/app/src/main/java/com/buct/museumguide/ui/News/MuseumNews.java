package com.buct.museumguide.ui.News;

public class MuseumNews {
    private int id;
    private String title;
    private String author;
    private String time;
    private String description;
    private String content;
    private String url;
    private int tag;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getTag() {
        return tag;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public String getDescription() {
        return description;
    }

    public String getTime() {
        return time;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
