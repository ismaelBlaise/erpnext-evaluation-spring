package com.evaluation.erpnext_spring.dto.payments;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class PaymentDTO {
    @JsonProperty("payment_type")
    private String paymentType;
    
    @JsonProperty("posting_date")
    private String postingDate;
    
    @JsonProperty("company")
    private String company;

    @JsonProperty("received_amount")
    private BigDecimal receivedAmount;

    @JsonProperty("source_exchange_rate")
    private BigDecimal sourceExchangeRate;
    
    @JsonProperty("paid_amount")
    private BigDecimal paidAmount;
    
    @JsonProperty("reference_no")
    private String referenceNo;
    
    @JsonProperty("reference_date")
    private String referenceDate;
    
    @JsonProperty("invoice_type")
    private String invoiceType = "Purchase Invoice";
    
    @JsonProperty("invoice_name")
    private String invoiceName;
    
    @JsonProperty("allocated_amount")
    private BigDecimal allocatedAmount;
    
    @JsonProperty("party_type")
    private String partyType = "Supplier";
    
    @JsonProperty("party")
    private String party; 
    
    @JsonProperty("mode_of_payment")
    private String modeOfPayment;

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("paid_from")
    private String paidFrom;
    
    @JsonProperty("paid_from_account_currency")
    private String paidFromAccountCurrency;
    
    @JsonProperty("paid_to")
    private String paidTo;
    
    @JsonProperty("paid_to_account_currency")
    private String paidToAccountCurrency = "USD"; 
    
    @JsonProperty("references")
    private List<PaymentReferenceDTO> references;
    
}