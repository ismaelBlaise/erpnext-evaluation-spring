package com.evaluation.erpnext_spring.dto.quotations;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class RequestForQuotationSupplierListResponse {
    @JsonProperty("data")
    private List<RequestForQuotationSupplierDTO> data;
}
