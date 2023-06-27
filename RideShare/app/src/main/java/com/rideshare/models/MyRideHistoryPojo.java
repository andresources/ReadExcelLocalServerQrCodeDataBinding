package com.rideshare.models;

import com.google.gson.annotations.SerializedName;

public class MyRideHistoryPojo {
    @SerializedName("amount")
    public String amount;


    @SerializedName("rid")
    private String rid;

    @SerializedName("bid")
    public String bid;

    @SerializedName("cid")
    public String cid;

    @SerializedName("dat")
    public String dat;

    @SerializedName("destination")
    public String destination;

    @SerializedName("frmuser")
    public String frmuser;

    @SerializedName("msg")
    public String msg;

    @SerializedName("seats")
    public String seats;

    @SerializedName("source")
    public String source;


    @SerializedName("status")
    public String status;

    @SerializedName("tim")
    public String tim;

    @SerializedName("touser")
    public String touser;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getDat() {
        return dat;
    }

    public void setDat(String dat) {
        this.dat = dat;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getFrmuser() {
        return frmuser;
    }

    public void setFrmuser(String frmuser) {
        this.frmuser = frmuser;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getSeats() {
        return seats;
    }

    public void setSeats(String seats) {
        this.seats = seats;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTim() {
        return tim;
    }

    public void setTim(String tim) {
        this.tim = tim;
    }

    public String getTouser() {
        return touser;
    }

    public void setTouser(String touser) {
        this.touser = touser;
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }
}
