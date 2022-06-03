package com.yuliia_koba.clean_digital_mobile.models.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.yuliia_koba.clean_digital_mobile.models.dto.User;

public class Laundry {
    static public final String LAUNDRY_ID = "LAUNDRY_ID";

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

    public String getEmail() {
        return user.getEmail();
    }

    public String getLaundryId() {
        return laundryId;
    }

    public String getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }
}

