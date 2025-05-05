package com.evaluation.erpnext_spring.dto.requests_for_quotation;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

import java.util.List;


@Data
public class RfqListResponse {
    @JsonProperty("status")
    private String status;
    
    @JsonProperty("data")
    private List<RfqDto> data;
    
    @JsonProperty("message")
    private String message;
    
    @JsonProperty("pagination")
    private PaginationDto pagination;

   
}