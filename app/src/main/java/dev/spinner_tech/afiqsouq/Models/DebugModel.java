package dev.spinner_tech.afiqsouq.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public  class DebugModel {

    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private Data data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
    public class Data {

        @SerializedName("status")
        @Expose
        private Integer status;
        @SerializedName("params")
        @Expose
        private String params;

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public String getParams() {
            return params;
        }

        public void setParams(String params) {
            this.params = params;
        }

    }

}
