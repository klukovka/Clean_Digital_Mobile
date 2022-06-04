package com.yuliia_koba.clean_digital_mobile.models.api;

public class UpdatePasswordRequest {
    private final String password;
    private final String newPassword;

    public UpdatePasswordRequest(String password, String newPassword) {
        this.password = password;
        this.newPassword = newPassword;
    }
}
