package com.buct.museumguide.bean;

public class UpdateAppResult {
    private int status;
    private  Appresult data;

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setData(Appresult data) {
        this.data = data;
    }

    public Appresult getData() {
        return data;
    }
    public static class Appresult{
        private String msg;
        private String version;
        private String name;
        private String description;

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public String getMsg() {
            return msg;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }

        public String getVersion() {
            return version;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public void setVersion(String version) {
            this.version = version;
        }
    }
}
