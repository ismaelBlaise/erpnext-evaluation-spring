package com.evaluation.erpnext_spring.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SupplierQuotationItemListResponse {

    @JsonProperty("data")
    private List<SupplierQuotationItemDto> data;

    public List<SupplierQuotationItemDto> getData() {
        return data;
    }

    public void setData(List<SupplierQuotationItemDto> data) {
        this.data = data;
    }
}


