package com.evaluation.erpnext_spring.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class SupplierListResponse {
    @JsonProperty("data")
    private List<SupplierDto> data;

    public SupplierListResponse() {}

    public SupplierListResponse(List<SupplierDto> data) {
        this.data = data;
    }

   
}