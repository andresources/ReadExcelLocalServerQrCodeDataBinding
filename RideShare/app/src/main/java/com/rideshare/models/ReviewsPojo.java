package com.rideshare.models;

import com.google.gson.annotations.SerializedName;

public class ReviewsPojo {
////SELECT `rating_id`, `rid`, `demail`, `name`, `email`, `msg`, `rating` FROM `rating` WHERE 1
    @SerializedName("rating_id")
    private
    String rating_id;

    @SerializedName("rid")
    private
    String rid;

    @SerializedName("demail")
    private
    String demail;

    @SerializedName("name")
    private
    String name;

    @SerializedName("email")
    private
    String email;

    @SerializedName("msg")
    private
    String msg;

    @SerializedName("rating")
    private
    String rating;

    public String getRating_id() {
        return rating_id;
    }

    public void setRating_id(String rating_id) {
        this.rating_id = rating_id;
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public String getDemail() {
        return demail;
    }

    public void setDemail(String demail) {
        this.demail = demail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
