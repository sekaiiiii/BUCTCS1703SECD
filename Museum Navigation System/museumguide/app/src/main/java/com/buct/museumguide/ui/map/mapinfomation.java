package com.buct.museumguide.ui.map;

import java.util.List;

import androidx.annotation.NonNull;

public class mapinfomation {
    private String status;
    private datas data;

    public String getStatus() {
        return status;
    }

    public datas getDatas() {
        return data;
    }

    public void setDatas(datas datas) {
        this.data = datas;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static  class datas{
        public List<position_list> getList() {
            return position_list;
        }

        public void setList(List<position_list> list) {
            this.position_list = list;
        }

        private List<position_list>position_list;
        public static  class position_list{
            private String id;
            private String name;
            private String longitude;
            private String latitude;

            public void setLatitude(String latitude) {
                this.latitude = latitude;
            }

            public String getLatitude() {
                return latitude;
            }

            @NonNull
            @Override
            public String toString() {
                return super.toString();
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getLongitude() {
                return longitude;
            }

            public String getId() {
                return id;
            }

            public String getName() {
                return name;
            }

            public void setId(String id) {
                this.id = id;
            }

            public void setLongitude(String longitude) {
                this.longitude = longitude;
            }
        }
    }
}
