package com.yuliia_koba.clean_digital_mobile.models.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RestorePasswordRequest {
    @SerializedName("email")
    @Expose
    private String email;

    public RestorePasswordRequest(String email){
        this.email = email;
    }
}
