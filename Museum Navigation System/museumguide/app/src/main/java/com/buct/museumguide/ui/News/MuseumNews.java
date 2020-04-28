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
    private String imgUrl;
    public MuseumNews(int id,String title,String author,String time,String description,String content,String url,int tag, String imgUrl){
        this.id=id;
        this.title=title;
        this.author=author;
        this.time=time;
        this.description=description;
        this.content=content;
        this.url=url;
        this.tag=tag;
        this.imgUrl = imgUrl;
    }
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

    public String getImgUrl() {
        return imgUrl;
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
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

}
