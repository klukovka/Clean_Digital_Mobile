package com.yuliia_koba.clean_digital_mobile.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EventPagination {
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
    private Event[] content;

    public EventPagination(int page, int size,
                             int totalPages, int totalElements,
                             Event[] content) {
        this.content = content;
        this.page = page;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }

    public Event[] getContent() {
        return content;
    }

    public int getTotalPages() {
        return totalPages;
    }
}
