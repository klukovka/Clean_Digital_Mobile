package com.yuliia_koba.clean_digital_mobile.models.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateClientRequest {
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("phone")
    @Expose
    private String phone;

    public UpdateClientRequest(String email,
                           String name, String phone){
        this.email = email;
        this.name = name;
        this.phone = phone;
    }
}
