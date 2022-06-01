package com.yuliia_koba.clean_digital_mobile.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Laundry {
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("laundryId")
    @Expose
    private String laundryId;
    @SerializedName("maxAmount")
    @Expose
    private int maxAmount;
    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("userId")
    @Expose
    private String userId;

    public Laundry(String name, String address,
                   String phone, String laundryId,
                   int maxAmount, User user, String userId){
        this.address = address;
        this.name = name;
        this.phone = phone;
        this.laundryId = laundryId;
        this.maxAmount = maxAmount;
        this.user = user;
        this.userId = userId;
    }
}

