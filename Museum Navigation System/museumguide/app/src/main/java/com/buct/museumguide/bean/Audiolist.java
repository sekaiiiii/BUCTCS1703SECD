package com.buct.museumguide.bean;

import android.app.Activity;

import com.buct.museumguide.ui.map.mapinfomation;

import java.util.List;

public  class Audiolist extends  HttpModel{
    private Audiolist.datas data;
    public Audiolist.datas getDatas() {
        return data;
    }

    public void setDatas(Audiolist.datas datas) {
        this.data = datas;
    }
   public  static class datas{
        private String msg;
        private List<explains> explain_list;
       public String getMsg() {
           return msg;
       }

       public void setExplain_list(List<explains> explain_list) {
           this.explain_list = explain_list;
       }
       public void setMsg(String msg) {
           this.msg = msg;
       }

       public List<explains> getExplain_list() {
           return explain_list;
       }
       public static class explains{
           private String id;
           private String  title;
           private String  artist;
           private String   album;
           private String   genre;
           private String    duration;
           private String   duration_unit;
           private String    file;
           private String    album_art_res_id;
           private String    album_art_res_name;
           private String     is_illegal;
           private String     user_id;
           private String    collection_id;
           private String      exhibition_id;
           private String      museum_id;
           private String      name;
           private String      mail_address;

           public void setTitle(String title) {
               this.title = title;
           }

           public String getTitle() {
               return title;
           }

           public void setId(String id) {
               this.id = id;
           }

           public void setName(String name) {
               this.name = name;
           }

           public void setDuration(String duration) {
               this.duration = duration;
           }

           public String getName() {
               return name;
           }

           public String getAlbum() {
               return album;
           }

           public String getAlbum_art_res_id() {
               return album_art_res_id;
           }

           public String getAlbum_art_res_name() {
               return album_art_res_name;
           }

           public String getArtist() {
               return artist;
           }

           public String getCollection_id() {
               return collection_id;
           }

           public String getDuration() {
               return duration;
           }

           public String getDuration_unit() {
               return duration_unit;
           }

           public String getExhibition_id() {
               return exhibition_id;
           }

           public String getFile() {
               return file;
           }

           public String getGenre() {
               return genre;
           }

           public String getId() {
               return id;
           }

           public String getIs_illegal() {
               return is_illegal;
           }

           public String getMail_address() {
               return mail_address;
           }

           public String getMuseum_id() {
               return museum_id;
           }

           public String getUser_id() {
               return user_id;
           }

           public void setAlbum(String album) {
               this.album = album;
           }

           public void setAlbum_art_res_id(String album_art_res_id) {
               this.album_art_res_id = album_art_res_id;
           }

           public void setAlbum_art_res_name(String album_art_res_name) {
               this.album_art_res_name = album_art_res_name;
           }

           public void setArtist(String artist) {
               this.artist = artist;
           }

           public void setCollection_id(String collection_id) {
               this.collection_id = collection_id;
           }

           public void setDuration_unit(String duration_unit) {
               this.duration_unit = duration_unit;
           }

           public void setExhibition_id(String exhibition_id) {
               this.exhibition_id = exhibition_id;
           }

           public void setFile(String file) {
               this.file = file;
           }

           public void setGenre(String genre) {
               this.genre = genre;
           }

           public void setIs_illegal(String is_illegal) {
               this.is_illegal = is_illegal;
           }

           public void setMail_address(String mail_address) {
               this.mail_address = mail_address;
           }

           public void setMuseum_id(String museum_id) {
               this.museum_id = museum_id;
           }

           public void setUser_id(String user_id) {
               this.user_id = user_id;
           }
       }
    }
}
