package com.evaluation.erpnext_spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.evaluation.erpnext_spring.dto.quotations.RequestForQuotationSupplierDTO;
import com.evaluation.erpnext_spring.dto.quotations.RequestForQuotationSupplierListResponse;

import jakarta.servlet.http.HttpSession;

import java.util.Collections;
import java.util.List;

@Service
public class RequestForQuotationSupplierService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${erpnext.api.url}")
    private String erpnextApiUrl;

    @Value("${erpnext.api.key}")
    private String erpnextApiKey;

    @Value("${erpnext.api.secret}")
    private String erpnextApiSecret;

    @SuppressWarnings("null")
    public List<RequestForQuotationSupplierDTO> getSuppliersBySupplierName(HttpSession session, String supplierName) {
        String sid = (String) session.getAttribute("sid");
        if (sid == null || sid.isEmpty()) {
            throw new RuntimeException("Session not authenticated");
        }

        if (supplierName == null || supplierName.isEmpty()) {
            throw new IllegalArgumentException("supplierName cannot be null or empty");
        }

        // Construction du filtre uniquement sur supplier_name
        String filters = String.format("[[\"supplier_name\", \"=\", \"%s\"]]", supplierName);

        String fields = "[\"name\",\"creation\",\"modified\",\"modified_by\",\"owner\",\"docstatus\",\"idx\",\"supplier\",\"contact\",\"quote_status\",\"supplier_name\",\"email_id\",\"send_email\",\"email_sent\",\"parent\",\"parentfield\",\"parenttype\"]";

        String url = String.format("%s/api/resource/Request for Quotation Supplier?filters=%s&fields=%s",
                erpnextApiUrl, filters, fields);

        System.out.println(url);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("Cookie", "sid=" + sid);
        headers.set("Authorization", "token " + erpnextApiKey + ":" + erpnextApiSecret);

        HttpEntity<String> request = new HttpEntity<>(headers);

        try {
            // Appel API ERPNext
            ResponseEntity<RequestForQuotationSupplierListResponse> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    request,
                    RequestForQuotationSupplierListResponse.class
            );

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                return response.getBody().getData();
            } else {
                throw new RuntimeException("Failed to fetch suppliers: " + response.getStatusCode());
            }

        } catch (Exception e) {
            throw new RuntimeException("Error while fetching suppliers: " + e.getMessage(), e);
        }
    }
}
