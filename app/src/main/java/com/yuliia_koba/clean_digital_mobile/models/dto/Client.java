package com.yuliia_koba.clean_digital_mobile.models.dto;

public class Client {
    public final String name;
    public final String phone;
    public final String clientId;
    public final String userId;
    public final Integer bonuses;
    public final Integer sale;
    public final User user;

    public Client(String name, String phone, String clientId, String userId, Integer bonuses, Integer sale, User user) {
        this.name = name;
        this.phone = phone;
        this.clientId = clientId;
        this.userId = userId;
        this.bonuses = bonuses;
        this.sale = sale;
        this.user = user;
    }
}


