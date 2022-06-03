package com.yuliia_koba.clean_digital_mobile.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class WashMachinePagination {
    @SerializedName("page")
    @Expose
    protected int page;
    @SerializedName("size")
    @Expose
    protected int size;
    @SerializedName("totalPages")
    @Expose
    protected int totalPages;
    @SerializedName("totalElements")
    @Expose
    protected int totalElements;
    @SerializedName("content")
    @Expose
    private WashMachine[] content;

    public WashMachinePagination(int page, int size,
                             int totalPages, int totalElements,
                             WashMachine[] content) {
        this.content = content;
        this.page = page;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }

    public WashMachine[] getContent() {
        return content;
    }

    public int getTotalPages() {
        return totalPages;
    }
}
