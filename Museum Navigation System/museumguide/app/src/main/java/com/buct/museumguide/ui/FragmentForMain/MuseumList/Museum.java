package com.buct.museumguide.ui.FragmentForMain.MuseumList;

import java.io.Serializable;

public class Museum implements Serializable {
    private static final long serialVersionUID = 3544092248974402737L;
    private int Id;
    private String name;
    private String level;
    private String number;
    private String letters; //名字首字母
    private String imgUrl;
    String exhibition_score;
    String environment_score;
    String service_score;
    String exhibition_num;
    String collection_num;
    private String latitude;
    private String longtitude;
    public Museum(String imgUrl, String name, String level, String exhibition_num,int id,String latitude,String longtitude){
        this.imgUrl =imgUrl;
        this.name = name;
        this.level =level;
        this.exhibition_num = exhibition_num;
        this.Id =id;
        this.latitude = latitude;
        this.longtitude = longtitude;
    }
    public Museum(String imgUrl, String name, String level, String collection_num,int id,String latitude,String longtitude,int x){
        this.imgUrl =imgUrl;
        this.name = name;
        this.level =level;
        this.collection_num = collection_num;
        this.Id = id;
        this.latitude =latitude;
        this.longtitude = longtitude;

    }
    public Museum(){

    }
    public Museum(String imgUrl, String name , String level, String number){
        this.imgUrl = imgUrl;
        this.name = name;
        this.level = level;
        this.number = number;
    }
    public Museum(String imgUrl, String name , String level, String number,String letters){
        this.imgUrl = imgUrl;
        this.name = name;
        this.level = level;
        this.number = number;
        this.letters = letters;
    }
    public Museum(String imgUrl, String name, String level, String exhibition_score, String environment_score, String service_score,int id, String latitude,String longtitude){
        this.imgUrl = imgUrl;
        this.name = name;
        this.level = level;
        this.exhibition_score = exhibition_score;
        this.environment_score =environment_score;
        this.service_score =service_score;
        this.Id = id;
        this.latitude = latitude;
        this.longtitude = longtitude;
    }

    public String getImgUrl() {return  imgUrl ;}
    public void setImgUrl(String imgUrl){ this.imgUrl = imgUrl ;}
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

    public int getId() {
        return Id;
    }

    public String getName() {
        return name;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public void setName(String name) {
        this.name = name;
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

    public void setExhibition_score(String exhibition_score) {
        this.exhibition_score = exhibition_score;
    }

    public void setEnvironment_score(String environment_score) {
        this.environment_score = environment_score;
    }

    public void setService_score(String service_score) {
        this.service_score = service_score;
    }

    public String getExhibition_num() {
        return exhibition_num;
    }

    public void setExhibition_num(String exhibition_num) {
        this.exhibition_num = exhibition_num;
    }

    public String getCollection_num() {
        return collection_num;
    }

    public void setCollection_num(String collection_num) {
        this.collection_num = collection_num;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongtitude() {
        return longtitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setLongtitude(String longtitude) {
        this.longtitude = longtitude;
    }

}
