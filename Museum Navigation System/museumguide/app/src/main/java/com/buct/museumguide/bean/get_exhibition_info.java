package com.buct.museumguide.bean;

import java.util.List;

import androidx.annotation.NonNull;

public class get_exhibition_info {
    private int status;
    private data1 data;

    public void setData(data1 data) {
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public data1 getData() {
        return data;
    }

    public static class data1{
        private String msg;
        private List<data2> exhibition_list;

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public List<data2> getExhibition_list() {
            return exhibition_list;
        }

        public void setExhibition_list(List<data2> exhibition_list) {
            this.exhibition_list = exhibition_list;
        }

        public static class data2{
            private int id;
            private String name;
            private String content;
            private String start_time;
            private String end_time;
            private String time;
            private String tag;
            private int museum_id;
            private List<String>image_list;

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public void setImage_list(List<String> image_list) {
                this.image_list = image_list;
            }

            public String getTime() {
                return time;
            }

            public int getId() {
                return id;
            }

            @NonNull
            @Override
            public String toString() {
                return super.toString();
            }

            public void setMuseum_id(int museum_id) {
                this.museum_id = museum_id;
            }

            public int getMuseum_id() {
                return museum_id;
            }

            public List<String> getImage_list() {
                return image_list;
            }

            public String getContent() {
                return content;
            }

            public String getEnd_time() {
                return end_time;
            }

            public String getStart_time() {
                return start_time;
            }

            public String getTag() {
                return tag;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public void setEnd_time(String end_time) {
                this.end_time = end_time;
            }

            public void setStart_time(String start_time) {
                this.start_time = start_time;
            }

            public void setTag(String tag) {
                this.tag = tag;
            }
        }
    }
}
