package com.buct.museumguide.ui.FragmentForMain.MuseumList;

public class Museum {
    private int imageId;
    private String name;
    private String level;
    private String number;
    private String letters; //名字首字母
    public Museum(){

    }
    public Museum(int imageId, String name , String level, String number){
        this.imageId = imageId;
        this.name = name;
        this.level = level;
        this.number = number;
    }
    public Museum(int imageId, String name , String level, String number,String letters){
        this.imageId = imageId;
        this.name = name;
        this.level = level;
        this.number = number;
        this.letters = letters;
    }

    public String getLetters() {
        return letters;
    }

    public void setLetters(String letters) {
        this.letters = letters;
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
