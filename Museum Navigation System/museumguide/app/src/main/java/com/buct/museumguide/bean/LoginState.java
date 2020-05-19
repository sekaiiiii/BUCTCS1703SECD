package com.buct.museumguide.bean;

import androidx.annotation.NonNull;

public class LoginState {
    private int status;
    private Datas data;
    public static class Datas{
        private String msg;
        private Boolean is_login;

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public String getMsg() {
            return msg;
        }

        @NonNull
        @Override
        public String toString() {
            return super.toString();
        }

        public Boolean getIs_login() {
            return is_login;
        }

        public void setIs_login(Boolean is_login) {
            this.is_login = is_login;
        }
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public Datas getData() {
        return data;
    }

    public void setData(Datas data) {
        this.data = data;
    }
}
