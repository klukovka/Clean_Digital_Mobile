package com.yuliia_koba.clean_digital_mobile.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LaundryPagination extends Pagination{
    @SerializedName("page")
    @Expose
    private List<Laundry> content;

    public LaundryPagination(int page, int size,
                             int totalPages, int totalElements,
                             List<Laundry> content) {
        super(page, size, totalPages, totalElements);
        this.content = content;
    }
}
