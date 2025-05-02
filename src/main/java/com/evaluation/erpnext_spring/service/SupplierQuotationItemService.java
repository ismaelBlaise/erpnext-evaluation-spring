package com.evaluation.erpnext_spring.service;

import com.evaluation.erpnext_spring.dto.SupplierQuotationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import jakarta.servlet.http.HttpSession;
import java.util.Collections;

@Service
public class SupplierQuotationService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${erpnext.api.url}")
    private String erpnextApiUrl;

    public SupplierQuotationResponse getSupplierQuotation(HttpSession session, String quotationId) {
        String sid = (String) session.getAttribute("sid");
        if (sid == null || sid.isEmpty()) {
            throw new RuntimeException("Session not authenticated");
        }

        // URL directe vers le document Supplier Quotation
        String url = String.format("%s/api/resource/Supplier Quotation/%s", erpnextApiUrl, quotationId);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("Cookie", "sid=" + sid);

        HttpEntity<String> request = new HttpEntity<>(headers);

        try {
            ResponseEntity<SupplierQuotationResponse> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    request,
                    SupplierQuotationResponse.class
            );

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                return response.getBody();
            } else {
                throw new RuntimeException("Failed to fetch supplier quotation: " + response.getStatusCode());
            }
        } catch (Exception e) {
            throw new RuntimeException("Error while fetching supplier quotation: " + e.getMessage(), e);
        }
    }
}
