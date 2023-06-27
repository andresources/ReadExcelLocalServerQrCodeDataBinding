package com.rideshare.models;

import com.google.gson.annotations.SerializedName;

public class msgs {

    @SerializedName("message")
    private
    String message;
    @SerializedName("frm")
    private
    String frm;
    @SerializedName("eto")
    private
    String eto;
    @SerializedName("rid")
    private
    String rid;


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFrm() {
        return frm;
    }

    public void setFrm(String frm) {
        this.frm = frm;
    }

    public String getEto() {
        return eto;
    }

    public void setEto(String eto) {
        this.eto = eto;
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }
}
