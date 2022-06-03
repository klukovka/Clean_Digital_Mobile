package com.yuliia_koba.clean_digital_mobile.models.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ApiError {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("statusCode")
    @Expose
    private int statusCode;

    public ApiError(Object message, int statusCode){
        this.message = message.toString();
        this.statusCode = statusCode;
    }
}
