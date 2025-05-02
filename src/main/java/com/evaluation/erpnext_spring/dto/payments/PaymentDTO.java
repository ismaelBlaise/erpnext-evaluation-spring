package com.evaluation.erpnext_spring.dto.payments;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class PaymentDTO {
    @JsonProperty("payment_type")
    private String paymentType;  
    
    @JsonProperty("posting_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date postingDate;
    
    @JsonProperty("company")
    private String company;
    
    @JsonProperty("paid_amount")
    private BigDecimal paidAmount;
    
    @JsonProperty("reference_no")
    private String referenceNo;
    
    @JsonProperty("reference_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date referenceDate;
    
    @JsonProperty("invoice_type")
    private String invoiceType = "Purchase Invoice";
    
    @JsonProperty("invoice_name")
    private String invoiceName;
    
    @JsonProperty("allocated_amount")
    private BigDecimal allocatedAmount;
    
    
}