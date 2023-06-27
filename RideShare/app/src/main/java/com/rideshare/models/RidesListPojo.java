package com.rideshare.models;

import com.google.gson.annotations.SerializedName;

public class RidesListPojo {

    @SerializedName("cid")
    public String cid;

    @SerializedName("cname")
    public String cname;

    @SerializedName("color")
    public String color;

    @SerializedName("cplate")
    public String cplate;

    @SerializedName("dat")
    public String dat;

    @SerializedName("destination")
    public String destination;

    @SerializedName("email")
    public String email;

    @SerializedName("photo")
    public String photo;

    @SerializedName("rid")
    public String rid;

    @SerializedName("seats")
    public String seats;

    @SerializedName("source")
    public String source;

    @SerializedName("tim")
    public String tim;

    @SerializedName("slat")
    private String slat;

    @SerializedName("slog")
    private String slog;

    @SerializedName("dlat")
    private String dlat;

    @SerializedName("dlog")
    private String dlog;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    @SerializedName("amount")
    public String amount;

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getCplate() {
        return cplate;
    }

    public void setCplate(String cplate) {
        this.cplate = cplate;
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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
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

    public String getSlat() {
        return slat;
    }

    public void setSlat(String slat) {
        this.slat = slat;
    }

    public String getSlog() {
        return slog;
    }

    public void setSlog(String slog) {
        this.slog = slog;
    }

    public String getDlat() {
        return dlat;
    }

    public void setDlat(String dlat) {
        this.dlat = dlat;
    }

    public String getDlog() {
        return dlog;
    }

    public void setDlog(String dlog) {
        this.dlog = dlog;
    }
}
