package com.yuliia_koba.clean_digital_mobile.services;

import com.yuliia_koba.clean_digital_mobile.models.api.UpdateClientRequest;
import com.yuliia_koba.clean_digital_mobile.models.dto.Client;
import com.yuliia_koba.clean_digital_mobile.models.pagination.EventPagination;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface ClientService {
    @GET("/client/client-info")
    Call<Client> getPersonalInfo(@Header("Authorization") String token);


    @PUT("/client/update-client")
    Call<Void> updateClientInfo(@Header("Authorization") String token,
                                @Body UpdateClientRequest body);
}
