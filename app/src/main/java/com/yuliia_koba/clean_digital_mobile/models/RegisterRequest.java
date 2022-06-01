package com.yuliia_koba.clean_digital_mobile.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegisterRequest {
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("phone")
    @Expose
    private String phone;

    public RegisterRequest(String email, String password,
                           String name, String phone){
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone = phone;
    }
}
