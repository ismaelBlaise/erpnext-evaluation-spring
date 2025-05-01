package com.evaluation.erpnext_spring.dto;

import lombok.Data;

@Data
public class SupplierDto {
    private String name;

    public SupplierDto() {}

    public SupplierDto(String name) {
        this.name = name;
    }

}