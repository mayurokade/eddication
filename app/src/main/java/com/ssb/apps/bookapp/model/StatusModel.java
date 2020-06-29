package com.ssb.apps.bookapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class StatusModel implements Serializable {
    @SerializedName("status")
    @Expose
    private boolean status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("error_data")
    @Expose
    private ErrorData errorData;

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ErrorData getErrorData() {
        return errorData;
    }

    public void setErrorData(ErrorData errorData) {
        this.errorData = errorData;
    }



    public class ErrorData {

        @SerializedName("mobile")
        @Expose
        private String mobile;

        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("password")
        @Expose
        private String password;


        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

    }
}
