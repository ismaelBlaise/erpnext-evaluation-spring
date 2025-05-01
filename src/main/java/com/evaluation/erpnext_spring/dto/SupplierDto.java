package com.evaluation.erpnext_spring.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class SupplierDto {
    @JsonProperty("name")
    private String name;

    public SupplierDto() {}

    public SupplierDto(String name) {
        this.name = name;
    }

}