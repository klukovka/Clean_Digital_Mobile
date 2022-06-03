package com.yuliia_koba.clean_digital_mobile.models;

public class WashMachine {
    private final String model;
    private final String manufacturer;
    private final int capacity;
    private final int powerUsage;
    private final int spinningSpeed;
    private final int maxTime;
    private final int currentTime;
    private final String washMachineId;
    private final String laundryId;
    private final boolean isWashing;
    private final boolean isWorking;


    public WashMachine(String model,
                       String manufacturer,
                       int capacity,
                       int powerUsage,
                       int spinningSpeed,
                       int maxTime,
                       int currentTime,
                       String washMachineId,
                       String laundryId,
                       boolean isWashing,
                       boolean isWorking) {
        this.model = model;
        this.manufacturer = manufacturer;
        this.capacity = capacity;
        this.powerUsage = powerUsage;
        this.spinningSpeed = spinningSpeed;
        this.maxTime = maxTime;
        this.currentTime = currentTime;
        this.washMachineId = washMachineId;
        this.laundryId = laundryId;
        this.isWashing = isWashing;
        this.isWorking = isWorking;
    }
}


