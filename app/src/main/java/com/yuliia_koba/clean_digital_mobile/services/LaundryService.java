package com.yuliia_koba.clean_digital_mobile.services;

import com.yuliia_koba.clean_digital_mobile.models.pagination.LaundryPagination;
import com.yuliia_koba.clean_digital_mobile.models.pagination.WashMachinePagination;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface LaundryService {
    @GET("/laundry/all")
    Call<LaundryPagination> getLaundries(
            @Query("page") int page,
            @Header("Authorization") String token);

    @GET("/laundry/all-washing-machines-users/{laundryId}")
    Call<WashMachinePagination> getWashMachines(
            @Path("laundryId") String laundryId,
            @Query("page") int page,
            @Header("Authorization") String token);
}
