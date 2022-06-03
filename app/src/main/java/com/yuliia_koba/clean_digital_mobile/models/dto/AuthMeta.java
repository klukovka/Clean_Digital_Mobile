package com.yuliia_koba.clean_digital_mobile.models.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AuthMeta {
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("role")
    @Expose
    private String role;

    public AuthMeta(String token, String userId, String email, String id, String role){
        this.email = email;
        this.id = id;
        this.token = token;
        this.userId = userId;
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public String getId() {
        return id;
    }

    public String getRole() {
        return role;
    }

    public String getToken() {
        return token;
    }

    public String getUserId() {
        return userId;
    }
}
