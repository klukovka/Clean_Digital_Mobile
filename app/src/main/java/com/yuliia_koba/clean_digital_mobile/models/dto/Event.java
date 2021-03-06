package com.yuliia_koba.clean_digital_mobile.models.dto;

import java.time.LocalDateTime;
import java.util.Date;

public class Event{
    static public final String EVENT_ID = "EVENT_ID";

    public final String eventId;
    public final String washMachineId;
    public final Integer temperature;
    public final Integer spinning;
    public final String modeId;
    public final Mode mode;
    public final String additionalModeId;
    public final AdditionalMode additionalMode;
    public final String clientId;
    public final Date timeBegin;
    public final Date timeEnd;
    public final Boolean paidStatus;
    public final Integer paidBonuses;
    public final Integer paidMoney;
    public final Boolean taken;
    public final Integer rating;

    public Event(String eventId, String washMachineId,
                 Integer temperature, Integer spinning,
                 String modeId, Mode mode, String additionalModeId,
                 AdditionalMode additionalMode, String clientId,
                 Date timeBegin, Date timeEnd,
                 Boolean paidStatus, Integer paidBonuses,
                 Integer paidMoney, Boolean taken,
                 Integer rating) {
        this.eventId = eventId;
        this.washMachineId = washMachineId;
        this.temperature = temperature;
        this.spinning = spinning;
        this.modeId = modeId;
        this.mode = mode;
        this.additionalModeId = additionalModeId;
        this.additionalMode = additionalMode;
        this.clientId = clientId;
        this.timeBegin = timeBegin;
        this.timeEnd = timeEnd;
        this.paidStatus = paidStatus;
        this.paidBonuses = paidBonuses;
        this.paidMoney = paidMoney;
        this.taken = taken;
        this.rating = rating;
    }

}
