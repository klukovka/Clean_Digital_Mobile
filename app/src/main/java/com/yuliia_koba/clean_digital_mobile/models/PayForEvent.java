package com.yuliia_koba.clean_digital_mobile.models;

public class PayForEvent {
    public final String eventId;
    public final int paidBonuses;

    public PayForEvent(String eventId, int paidBonuses) {
        this.eventId = eventId;
        this.paidBonuses = paidBonuses;
    }
}
