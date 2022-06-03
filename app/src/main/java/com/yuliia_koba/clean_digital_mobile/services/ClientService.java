package com.yuliia_koba.clean_digital_mobile.services;

import com.yuliia_koba.clean_digital_mobile.models.dto.Client;
import com.yuliia_koba.clean_digital_mobile.models.pagination.EventPagination;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface ClientService {
    @GET("/client/client-info")
    Call<Client> getPersonalInfo(@Header("Authorization") String token);
}
