package com.yuliia_koba.clean_digital_mobile.services;

import com.yuliia_koba.clean_digital_mobile.models.EventPagination;
import com.yuliia_koba.clean_digital_mobile.models.PayForEvent;
import com.yuliia_koba.clean_digital_mobile.models.RateEvent;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface EventService {
    @GET("/event/client-events")
    Call<EventPagination> getEvents(
            @Query("page") int page,
            @Header("Authorization") String token);


    @POST("/event/pay-for-event")
    Call<Void> payForEvent(
            @Body PayForEvent body,
            @Header("Authorization") String token);

    @POST("/event/take-event/{eventId}")
    Call<Void> takeEvent(
            @Path("eventId") String eventId,
            @Header("Authorization") String token);

    @POST("/event/rate-event/{eventId}")
    Call<Void> rateEvent(
            @Path("eventId") String eventId,
            @Body RateEvent body,
            @Header("Authorization") String token);
}
