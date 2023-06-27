package com.rideshare.models;

import com.google.gson.annotations.SerializedName;

public class EditProfilePojo {

    @SerializedName("email")
    public String email;

    @SerializedName("gender")
    public String gender;

    @SerializedName("name")
    public String name;

    @SerializedName("password")
    public String password;

    @SerializedName("phone")
    public String phone;

    @SerializedName("photo")
    public String photo;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public EditProfilePojo(String email, String gender, String name, String password, String phone, String photo) {
        this.email = email;
        this.gender = gender;
        this.name = name;
        this.password = password;
        this.phone = phone;
        this.photo = photo;
    }
}
