package com.buct.museumguide.ui.FragmentForUsers.Upload;

import androidx.annotation.NonNull;

public class audioitem {
private String title;
private String filename;
private String author;
private String id;
    public audioitem(String a, String b,String c,String d){
        this.title=a;this.filename=b;this.author=c;id=d;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }

    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
