package com.evaluation.erpnext_spring.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RequestForQuotationSupplierDTO {

    @JsonProperty("name")
    private String name;

    @JsonProperty("creation")
    private LocalDateTime creation;

    @JsonProperty("modified")
    private LocalDateTime modified;

    @JsonProperty("modified_by")
    private String modifiedBy;

    @JsonProperty("owner")
    private String owner;

    @JsonProperty("docstatus")
    private Integer docstatus;

    @JsonProperty("idx")
    private Integer idx;

    @JsonProperty("supplier")
    private String supplier;

    @JsonProperty("contact")
    private String contact;

    @JsonProperty("quote_status")
    private String quoteStatus;

    @JsonProperty("supplier_name")
    private String supplierName;

    @JsonProperty("email_id")
    private String emailId;

    @JsonProperty("send_email")
    private Integer sendEmail;

    @JsonProperty("email_sent")
    private Integer emailSent;

    @JsonProperty("parent")
    private String parent;

    @JsonProperty("parentfield")
    private String parentfield;

    @JsonProperty("parenttype")
    private String parenttype;
}
