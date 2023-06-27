package com.rideshare.models;

import com.google.gson.annotations.SerializedName;

public class CarDetailsPojo {

    @SerializedName("cid")
    public String cid;

    @SerializedName("cname")
    public String cname;

    @SerializedName("color")
    public String color;

    @SerializedName("cplate")
    public String cplate;

    @SerializedName("email")
    public String email;


    @SerializedName("seats")
    public String seats;

    @SerializedName("photo")
    public String photo;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSeats() {
        return seats;
    }

    public void setSeats(String seats) {
        this.seats = seats;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
