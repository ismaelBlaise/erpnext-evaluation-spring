package com.evaluation.erpnext_spring.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class RequestForQuotationListResponse {

    @JsonProperty("data")
    private List<RequestForQuotationDTO> data;
}
