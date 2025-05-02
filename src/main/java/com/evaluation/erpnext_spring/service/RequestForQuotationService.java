package com.evaluation.erpnext_spring.service;

import com.evaluation.erpnext_spring.dto.RequestForQuotationListResponse;
import com.evaluation.erpnext_spring.dto.RequestForQuotationSupplierDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import jakarta.servlet.http.HttpSession;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RequestForQuotationService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${erpnext.api.url}")
    private String erpnextApiUrl;

    public RequestForQuotationListResponse getQuotationsBySuppliers(HttpSession session, List<RequestForQuotationSupplierDTO> supplierDTOList) {
        String sid = (String) session.getAttribute("sid");
        if (sid == null || sid.isEmpty()) {
            throw new RuntimeException("Session not authenticated");
        }

        if (supplierDTOList == null || supplierDTOList.isEmpty()) {
            return new RequestForQuotationListResponse();  
        }

        List<String> parentIds = supplierDTOList.stream()
                .map(RequestForQuotationSupplierDTO::getParent)
                .distinct()
                .collect(Collectors.toList());

         
        StringBuilder filterBuilder = new StringBuilder("[[\"name\",\"in\",[");
        for (int i = 0; i < parentIds.size(); i++) {
            filterBuilder.append("\"").append(parentIds.get(i)).append("\"");
            if (i < parentIds.size() - 1) filterBuilder.append(",");
        }
        filterBuilder.append("]]]");

        String fields = "[\"name\",\"creation\",\"modified\",\"supplier\",\"supplier_name\",\"transaction_date\",\"status\",\"message_for_supplier\",\"incoterm\",\"total\"]";

        String url = String.format("%s/api/resource/Request for Quotation?filters=%s&fields=%s", 
                erpnextApiUrl, filterBuilder.toString(), fields);

        System.out.println(url);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("Cookie", "sid=" + sid);

        HttpEntity<String> request = new HttpEntity<>(headers);

        try {
            
            ResponseEntity<RequestForQuotationListResponse> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    request,
                    RequestForQuotationListResponse.class
            );

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                return response.getBody();
            } else {
                throw new RuntimeException("Failed to fetch Request for Quotations: " + response.getStatusCode());
            }

        } catch (Exception e) {
            throw new RuntimeException("Error while fetching Request for Quotations: " + e.getMessage(), e);
        }
    }
}
