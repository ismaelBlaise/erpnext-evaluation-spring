package com.evaluation.erpnext_spring.service;

import com.evaluation.erpnext_spring.dto.SupplierQuotationItemListResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import jakarta.servlet.http.HttpSession;
import java.util.Collections;

@Service
public class SupplierQuotationItemService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${erpnext.api.url}")
    private String erpnextApiUrl;

    public SupplierQuotationItemListResponse getItemsByParent(HttpSession session, String parentId, int page, int size) {
        String sid = (String) session.getAttribute("sid");
        if (sid == null || sid.isEmpty()) {
            throw new RuntimeException("Session not authenticated");
        }

        int offset = page * size;

        String fields = "[" +
                "\"name\",\"creation\",\"modified\",\"modified_by\",\"owner\",\"docstatus\",\"idx\",\"item_code\"," +
                "\"supplier_part_no\",\"item_name\",\"lead_time_days\",\"expected_delivery_date\",\"is_free_item\"," +
                "\"description\",\"item_group\",\"brand\",\"image\",\"qty\",\"stock_uom\",\"uom\",\"conversion_factor\"," +
                "\"stock_qty\",\"price_list_rate\",\"discount_percentage\",\"discount_amount\",\"distributed_discount_amount\"," +
                "\"base_price_list_rate\",\"rate\",\"amount\",\"item_tax_template\",\"base_rate\",\"base_amount\"," +
                "\"pricing_rules\",\"net_rate\",\"net_amount\",\"base_net_rate\",\"base_net_amount\",\"weight_per_unit\"," +
                "\"total_weight\",\"weight_uom\",\"warehouse\",\"prevdoc_doctype\",\"material_request\",\"sales_order\"," +
                "\"request_for_quotation\",\"material_request_item\",\"request_for_quotation_item\",\"item_tax_rate\"," +
                "\"manufacturer\",\"manufacturer_part_no\",\"cost_center\",\"project\",\"page_break\",\"parent\"," +
                "\"parentfield\",\"parenttype\"" +
                "]";

        String filters = String.format("[[\"parent\", \"=\", \"%s\"]]", parentId);

        String url = String.format("%s/api/resource/Supplier Quotation Item?filters=%s&fields=%s&limit_start=%d&limit_page_length=%d",
                erpnextApiUrl, filters, fields, offset, size);

        System.out.println(url);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("Cookie", "sid=" + sid);

        HttpEntity<String> request = new HttpEntity<>(headers);

        try {
            ResponseEntity<SupplierQuotationItemListResponse> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    request,
                    SupplierQuotationItemListResponse.class
            );

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                return response.getBody();
            } else {
                throw new RuntimeException("Failed to fetch supplier quotation items: " + response.getStatusCode());
            }
        } catch (Exception e) {
            throw new RuntimeException("Error while fetching supplier quotation items: " + e.getMessage(), e);
        }
    }
}
