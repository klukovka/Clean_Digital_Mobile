package com.yuliia_koba.clean_digital_mobile.models.api;

public class PayForEventRequest {
    public final String eventId;
    public final int paidBonuses;

    public PayForEventRequest(String eventId, int paidBonuses) {
        this.eventId = eventId;
        this.paidBonuses = paidBonuses;
    }
}
