package com.evaluation.erpnext_spring.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SupplierQuotationItemDto {

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

    @JsonProperty("item_code")
    private String itemCode;

    @JsonProperty("supplier_part_no")
    private String supplierPartNo;

    @JsonProperty("item_name")
    private String itemName;

    @JsonProperty("lead_time_days")
    private Integer leadTimeDays;

    @JsonProperty("expected_delivery_date")
    private String expectedDeliveryDate;

    @JsonProperty("is_free_item")
    private Integer isFreeItem;

    @JsonProperty("description")
    private String description;

    @JsonProperty("item_group")
    private String itemGroup;

    @JsonProperty("brand")
    private String brand;

    @JsonProperty("image")
    private String image;

    @JsonProperty("qty")
    private Double qty;

    @JsonProperty("stock_uom")
    private String stockUom;

    @JsonProperty("uom")
    private String uom;

    @JsonProperty("conversion_factor")
    private Double conversionFactor;

    @JsonProperty("stock_qty")
    private Double stockQty;

    @JsonProperty("price_list_rate")
    private Double priceListRate;

    @JsonProperty("discount_percentage")
    private Double discountPercentage;

    @JsonProperty("discount_amount")
    private Double discountAmount;

    @JsonProperty("distributed_discount_amount")
    private Double distributedDiscountAmount;

    @JsonProperty("base_price_list_rate")
    private Double basePriceListRate;

    @JsonProperty("rate")
    private Double rate;

    @JsonProperty("amount")
    private Double amount;

    @JsonProperty("item_tax_template")
    private String itemTaxTemplate;

    @JsonProperty("base_rate")
    private Double baseRate;

    @JsonProperty("base_amount")
    private Double baseAmount;

    @JsonProperty("pricing_rules")
    private String pricingRules;

    @JsonProperty("net_rate")
    private Double netRate;

    @JsonProperty("net_amount")
    private Double netAmount;

    @JsonProperty("base_net_rate")
    private Double baseNetRate;

    @JsonProperty("base_net_amount")
    private Double baseNetAmount;

    @JsonProperty("weight_per_unit")
    private Double weightPerUnit;

    @JsonProperty("total_weight")
    private Double totalWeight;

    @JsonProperty("weight_uom")
    private String weightUom;

    @JsonProperty("warehouse")
    private String warehouse;

    @JsonProperty("prevdoc_doctype")
    private String prevdocDoctype;

    @JsonProperty("material_request")
    private String materialRequest;

    @JsonProperty("sales_order")
    private String salesOrder;

    @JsonProperty("request_for_quotation")
    private String requestForQuotation;

    @JsonProperty("material_request_item")
    private String materialRequestItem;

    @JsonProperty("request_for_quotation_item")
    private String requestForQuotationItem;

    @JsonProperty("item_tax_rate")
    private String itemTaxRate;

    @JsonProperty("manufacturer")
    private String manufacturer;

    @JsonProperty("manufacturer_part_no")
    private String manufacturerPartNo;

    @JsonProperty("cost_center")
    private String costCenter;

    @JsonProperty("project")
    private String project;

    @JsonProperty("page_break")
    private Integer pageBreak;

    @JsonProperty("parent")
    private String parent;

    @JsonProperty("parentfield")
    private String parentfield;

    @JsonProperty("parenttype")
    private String parenttype;

}
