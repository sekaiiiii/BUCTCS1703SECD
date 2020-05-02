package com.buct.museumguide.ui.FragmentForMain.MuseumList;

public class Museum {
    private int imageId;
    private String name;
    private String level;
    private String number;
    private String test;
    public Museum(int imageId, String name , String level, String number){
        this.imageId = imageId;
        this.name = name;
        this.level = level;
        this.number = number;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public String getLevel() {
        return level;
    }

    public String getNumber() {
        return number;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getImageId() {
        return imageId;
    }

    public String getName() {
        return name;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public void setName(String name) {
        this.name = name;
    }
}
