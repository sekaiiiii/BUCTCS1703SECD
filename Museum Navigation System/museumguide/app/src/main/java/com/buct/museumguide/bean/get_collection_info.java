package com.buct.museumguide.bean;

import java.util.List;

import androidx.annotation.NonNull;

public class get_collection_info {
    private int status;
    private data1 data;

    public data1 getData() {
        return data;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setData(data1 data) {
        this.data = data;
    }

    public static class data1{
        private String msg;
        private List<data2> collection_list;

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public String getMsg() {
            return msg;
        }

        public List<data2> getCollection_list() {
            return collection_list;
        }

        public void setCollection_list(List<data2> collection_list) {
            this.collection_list = collection_list;
        }

        public static class data2{
            private int id;
            private String name;
            private String content;
            private String material;
            private int tag;
            private int museum_id;
            private List<String>image_list;

            @NonNull
            @Override
            public String toString() {
                return super.toString();
            }

            public void setTag(int tag) {
                this.tag = tag;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getContent() {
                return content;
            }

            public List<String> getImage_list() {
                return image_list;
            }

            public int getMuseum_id() {
                return museum_id;
            }

            public void setMuseum_id(int museum_id) {
                this.museum_id = museum_id;
            }

            public int getId() {
                return id;
            }

            public void setImage_list(List<String> image_list) {
                this.image_list = image_list;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getName() {
                return name;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getTag() {
                return tag;
            }

            public String getMaterial() {
                return material;
            }

            public void setMaterial(String material) {
                this.material = material;
            }
        }
    }
}
