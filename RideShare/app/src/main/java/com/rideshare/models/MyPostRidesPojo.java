package com.rideshare.models;

import com.google.gson.annotations.SerializedName;

public class MyPostRidesPojo {

    @SerializedName("amount")
    public String amount;

    @SerializedName("cid")
    public String cid;

    @SerializedName("dat")
    public String dat;

    @SerializedName("destination")
    public String destination;

    @SerializedName("email")
    public String email;

    @SerializedName("rid")
    public String rid;

    @SerializedName("seats")
    public String seats;

    @SerializedName("source")
    public String source;

    @SerializedName("tim")
    public String tim;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
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

    public String getTim() {
        return tim;
    }

    public void setTim(String tim) {
        this.tim = tim;
    }
}
