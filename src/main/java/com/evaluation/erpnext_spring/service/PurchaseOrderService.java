package com.evaluation.erpnext_spring.service;

import com.evaluation.erpnext_spring.dto.PurchaseOrderListResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import jakarta.servlet.http.HttpSession;

import java.util.Collections;

@Service
public class PurchaseOrderService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${erpnext.api.url}")
    private String erpnextApiUrl;

    public PurchaseOrderListResponse getPurchaseOrdersBySupplier(HttpSession session, String supplierId, int page, int size) {
        String sid = (String) session.getAttribute("sid");
        if (sid == null || sid.isEmpty()) {
            throw new RuntimeException("Session not authenticated");
        }

        int offset = page * size;

        String fields = "[\"name\",\"creation\",\"modified\",\"supplier\",\"supplier_name\",\"order_confirmation_no\"," +
                "\"total_qty\",\"base_total\",\"base_grand_total\",\"grand_total\",\"rounded_total\",\"status\",\"transaction_date\"]";

        String filters = String.format("[[\"supplier\", \"=\", \"%s\"]]", supplierId);

        String url = String.format("%s/api/resource/Purchase Order?filters=%s&fields=%s&limit_start=%d&limit_page_length=%d",
                erpnextApiUrl, filters, fields,  offset,size);

        System.out.println(url);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("Cookie", "sid=" + sid);

        HttpEntity<String> request = new HttpEntity<>(headers);

        try {
            ResponseEntity<PurchaseOrderListResponse> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    request,
                    PurchaseOrderListResponse.class
            );

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                return response.getBody();
            } else {
                throw new RuntimeException("Failed to fetch purchase orders: " + response.getStatusCode());
            }
        } catch (Exception e) {
            throw new RuntimeException("Error while fetching purchase orders: " + e.getMessage(), e);
        }
    }
}
