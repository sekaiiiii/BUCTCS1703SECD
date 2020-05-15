package com.buct.museumguide.bean;

import java.io.Serializable;
import java.util.ArrayList;

import androidx.annotation.NonNull;

public class Museum_Info_Full implements Serializable{
    private int status;
    private museuminfo data;
    public static class museuminfo implements Serializable {
        private String msg;
        private ArrayList<realdata> museum_list;
        public static class realdata implements Serializable{
            private int id;
            private String name;
            private String establishment_time;
            private String open_time;
            private String close_time;
            private String time;
            private String introduction;
            private String visit_info;
            private String attention;
            private Double exhibition_score;
            private Double environment_score;
            private Double service_score;
            private String position_name;
            private Double longitude;
            private Double latitude;
            private ArrayList<String>image_list;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public void setId(int id) {
                this.id = id;
            }

            public void setLongitude(Double longitude) {
                this.longitude = longitude;
            }

            public int getId() {
                return id;
            }

            public Double getEnvironment_score() {
                return environment_score;
            }

            public void setLatitude(Double latitude) {
                this.latitude = latitude;
            }

            public Double getExhibition_score() {
                return exhibition_score;
            }

            public Double getLatitude() {
                return latitude;
            }

            public Double getLongitude() {
                return longitude;
            }

            public Double getService_score() {
                return service_score;
            }

            public String getAttention() {
                return attention;
            }

            public String getClose_time() {
                return close_time;
            }

            public ArrayList<String> getImage_list() {
                return image_list;
            }

            public String getEstablishment_time() {
                return establishment_time;
            }

            public String getIntroduction() {
                return introduction;
            }

            public String getOpen_time() {
                return open_time;
            }

            public String getPosition_name() {
                return position_name;
            }

            public String getTime() {
                return time;
            }

            @NonNull
            @Override
            public String toString() {
                return super.toString();
            }

            public String getVisit_info() {
                return visit_info;
            }

            public void setAttention(String attention) {
                this.attention = attention;
            }

            public void setClose_time(String close_time) {
                this.close_time = close_time;
            }

            public void setEnvironment_score(Double environment_score) {
                this.environment_score = environment_score;
            }

            public void setEstablishment_time(String establishment_time) {
                this.establishment_time = establishment_time;
            }

            public void setExhibition_score(Double exhibition_score) {
                this.exhibition_score = exhibition_score;
            }

            public void setImage_list(ArrayList<String> image_list) {
                this.image_list = image_list;
            }

            public void setIntroduction(String introduction) {
                this.introduction = introduction;
            }

            public void setOpen_time(String open_time) {
                this.open_time = open_time;
            }

            public void setPosition_name(String position_name) {
                this.position_name = position_name;
            }

            public void setService_score(Double service_score) {
                this.service_score = service_score;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public void setVisit_info(String visit_info) {
                this.visit_info = visit_info;
            }
        }

        public String getMsg() {
            return msg;
        }

        @NonNull
        @Override
        public String toString() {
            return super.toString();
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public ArrayList<realdata> getMuseum_list() {
            return museum_list;
        }

        public void setMuseum_list(ArrayList<realdata> museum_list) {
            this.museum_list = museum_list;
        }
    }

    public void setData(museuminfo data) {
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public museuminfo getData() {
        return data;
    }
}
