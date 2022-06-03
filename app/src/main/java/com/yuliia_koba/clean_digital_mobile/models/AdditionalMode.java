package com.yuliia_koba.clean_digital_mobile.models;

public class AdditionalMode {
    public final int costs;
    public final int time;
    public final String additionlModeId;
    public final String name;

    public AdditionalMode(int costs, int time,
                          String additionlModeId, String name) {
        this.costs = costs;
        this.time = time;
        this.additionlModeId = additionlModeId;
        this.name = name;
    }
}