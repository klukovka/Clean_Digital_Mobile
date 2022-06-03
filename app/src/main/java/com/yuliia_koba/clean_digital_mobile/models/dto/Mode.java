package com.yuliia_koba.clean_digital_mobile.models.dto;

public class Mode {
    public final int costs;
    public final int time;
    public final String modeId;
    public final String name;

    public Mode(int costs, int time, String modeId, String name) {
        this.costs = costs;
        this.time = time;
        this.modeId = modeId;
        this.name = name;
    }
}