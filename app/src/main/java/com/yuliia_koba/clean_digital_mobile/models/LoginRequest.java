package com.yuliia_koba.clean_digital_mobile.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginRequest {
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("password")
    @Expose
    private String password;

    public LoginRequest(String email, String password){
        this.email = email;
        this.password = password;
    }
}
