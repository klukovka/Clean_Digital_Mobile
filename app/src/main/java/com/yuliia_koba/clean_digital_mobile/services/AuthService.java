package com.yuliia_koba.clean_digital_mobile.services;

import com.yuliia_koba.clean_digital_mobile.models.AuthMeta;
import com.yuliia_koba.clean_digital_mobile.models.LoginRequest;
import com.yuliia_koba.clean_digital_mobile.models.RegisterRequest;
import com.yuliia_koba.clean_digital_mobile.models.RestorePasswordRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthService {
    @POST("/auth/login")
    Call<AuthMeta> login(@Body LoginRequest request);

    @POST("/auth/login")
    Call<Void> restore(@Body RestorePasswordRequest request);

    @POST("/auth/signup-client")
    Call<Void> register(@Body RegisterRequest request);
}
