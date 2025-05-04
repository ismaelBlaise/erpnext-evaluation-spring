package com.evaluation.erpnext_spring.dto.quotations;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RequestForQuotationDTO {

    @JsonProperty("name")
    private String name;

    @JsonProperty("creation")
    private String creation;

    @JsonProperty("modified")
    private String modified;

    @JsonProperty("modified_by")
    private String modifiedBy;

    @JsonProperty("owner")
    private String owner;

    @JsonProperty("docstatus")
    private Integer docstatus;

    @JsonProperty("idx")
    private Integer idx;

    @JsonProperty("naming_series")
    private String namingSeries;

    @JsonProperty("company")
    private String company;

    @JsonProperty("billing_address")
    private String billingAddress;

    @JsonProperty("billing_address_display")
    private String billingAddressDisplay;

    @JsonProperty("vendor")
    private String vendor;

    @JsonProperty("transaction_date")
    private String transactionDate;

    @JsonProperty("schedule_date")
    private String scheduleDate;

    @JsonProperty("status")
    private String status;

    @JsonProperty("amended_from")
    private String amendedFrom;

    @JsonProperty("email_template")
    private String emailTemplate;

    @JsonProperty("send_attached_files")
    private Boolean sendAttachedFiles;

    @JsonProperty("send_document_print")
    private Boolean sendDocumentPrint;

    @JsonProperty("message_for_supplier")
    private String messageForSupplier;

    @JsonProperty("incoterm")
    private String incoterm;

    @JsonProperty("named_place")
    private String namedPlace;

    @JsonProperty("tc_name")
    private String tcName;

    @JsonProperty("terms")
    private String terms;

    @JsonProperty("select_print_heading")
    private String selectPrintHeading;

    @JsonProperty("letter_head")
    private String letterHead;

    @JsonProperty("opportunity")
    private String opportunity;

    @JsonProperty("_user_tags")
    private String userTags;

    @JsonProperty("_comments")
    private String comments;

    @JsonProperty("_assign")
    private String assign;

    @JsonProperty("_liked_by")
    private String likedBy;
}
