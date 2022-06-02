package com.yuliia_koba.clean_digital_mobile.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("userId")
    @Expose
    private String userId;

    public User(String email, String userId){
        this.email = email;
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }
}
