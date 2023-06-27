package com.rideshare.models;

import com.google.gson.annotations.SerializedName;

public class ToppersPojo {

    @SerializedName("demail")
    private
    String demail;
    @SerializedName("star")
    private
    String star;

    public String getDemail() {
        return demail;
    }

    public void setDemail(String demail) {
        this.demail = demail;
    }

    public String getStar() {
        return star;
    }

    public void setStar(String star) {
        this.star = star;
    }
}
