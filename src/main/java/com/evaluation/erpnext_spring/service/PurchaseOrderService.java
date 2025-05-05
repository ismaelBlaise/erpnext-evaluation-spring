package com.evaluation.erpnext_spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.evaluation.erpnext_spring.dto.orders.PurchaseOrderDTO;
import com.evaluation.erpnext_spring.dto.orders.PurchaseOrderListResponse;

import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class PurchaseOrderService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${erpnext.api.url}")
    private String erpnextApiUrl;

    @Value("${erpnext.api.key}")
    private String erpnextApiKey;

    @Value("${erpnext.api.secret}")
    private String erpnextApiSecret;

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
        headers.set("Authorization", "token " + erpnextApiKey + ":" + erpnextApiSecret);

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


    public PurchaseOrderListResponse getPurchaseOrdersBySupplierAndStatus(HttpSession session, String supplierId, String status) {
        String sid = (String) session.getAttribute("sid");
        if (sid == null || sid.isEmpty()) {
            throw new RuntimeException("Session not authenticated");
        }
    
        String fields = "[\"name\",\"creation\",\"modified\",\"supplier\",\"supplier_name\",\"order_confirmation_no\"," +
                "\"total_qty\",\"base_total\",\"base_grand_total\",\"grand_total\",\"rounded_total\",\"status\",\"transaction_date\"]";
    
        
        String filters = String.format("[[\"supplier\", \"=\", \"%s\"]]", supplierId);
    
        
        if (status != null && !status.isEmpty()) {
            filters = String.format("[[\"supplier\", \"=\", \"%s\"], [\"status\", \"=\", \"%s\"]]", supplierId, status);
        }
    
        
        String url = String.format("%s/api/resource/Purchase Order?filters=%s&fields=%s", 
                                   erpnextApiUrl, filters, fields);
    
        System.out.println(url);
    
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("Cookie", "sid=" + sid);
        headers.set("Authorization", "token " + erpnextApiKey + ":" + erpnextApiSecret);
    
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




    // public PurchaseOrderListResponse getPurchaseOrdersBySupplierAndStatus(HttpSession session, String supplierId, String status) {
    //     String sid = (String) session.getAttribute("sid");
    //     if (sid == null || sid.isEmpty()) {
    //         throw new RuntimeException("Session not authenticated");
    //     }

    //     String fields = "[\"name\",\"creation\",\"modified\",\"supplier\",\"supplier_name\",\"order_confirmation_no\"," +
    //             "\"total_qty\",\"base_total\",\"base_grand_total\",\"grand_total\",\"rounded_total\",\"status\",\"transaction_date\"]";

    //     String filters = String.format("[[\"supplier\", \"=\", \"%s\"]]", supplierId);

    //     String url = String.format("%s/api/resource/Purchase Order?filters=%s&fields=%s", erpnextApiUrl, filters, fields);

    //     HttpHeaders headers = new HttpHeaders();
    //     headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    //     headers.add("Cookie", "sid=" + sid);
    //     headers.set("Authorization", "token " + erpnextApiKey + ":" + erpnextApiSecret);

    //     HttpEntity<String> request = new HttpEntity<>(headers);

    //     try {
    //         ResponseEntity<PurchaseOrderListResponse> response = restTemplate.exchange(
    //                 url,
    //                 HttpMethod.GET,
    //                 request,
    //                 PurchaseOrderListResponse.class
    //         );

    //         if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
    //             // On filtre la liste ici
    //             List<PurchaseOrderDTO> filteredOrders = new ArrayList<>();
    //             for (PurchaseOrderDTO order : response.getBody().getData()) {
    //                 if ("payer".equalsIgnoreCase(status)) {
    //                     if (isOrderPaid(order.getName(), sid)) {
    //                         filteredOrders.add(order);
    //                     }
    //                 } else if ("recu".equalsIgnoreCase(status)) {
    //                     if (isOrderReceived(order.getName(), sid)) {
    //                         filteredOrders.add(order);
    //                     }
    //                 } else {
    //                     filteredOrders.add(order); // si pas de filtre spécifique
    //                 }
    //             }
    //             response.getBody().setData(filteredOrders);
    //             return response.getBody();
    //         } else {
    //             throw new RuntimeException("Failed to fetch purchase orders: " + response.getStatusCode());
    //         }
    //     } catch (Exception e) {
    //         throw new RuntimeException("Error while fetching purchase orders: " + e.getMessage(), e);
    //     }
    // }

    // private boolean isOrderReceived(String purchaseOrderName, String sid) {
    //     String filters = String.format("[[\"purchase_order\", \"=\", \"%s\"]]", purchaseOrderName);
    //     String url = String.format("%s/api/resource/Purchase Receipt Item?filters=%s&fields=[\"parent\"]", erpnextApiUrl, filters);
    
    //     HttpHeaders headers = new HttpHeaders();
    //     headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    //     headers.add("Cookie", "sid=" + sid);
    //     headers.set("Authorization", "token " + erpnextApiKey + ":" + erpnextApiSecret);
    
    //     HttpEntity<String> request = new HttpEntity<>(headers);
    
    //     try {
    //         ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, request, Map.class);
    //         List<Map<String, Object>> data = (List<Map<String, Object>>) response.getBody().get("data");
    
    //         if (data == null || data.isEmpty()) {
    //             return false;
    //         }
    
    //         for (Map<String, Object> item : data) {
    //             String parentReceipt = (String) item.get("parent");
    //             if (parentReceipt != null) {
    //                 // Vérifier si le parent Purchase Receipt est validé
    //                 if (isReceiptValidated(parentReceipt, sid)) {
    //                     return true;
    //                 }
    //             }
    //         }
    //         return false;
    //     } catch (Exception e) {
    //         return false;
    //     }
    // }
    
    // private boolean isReceiptValidated(String receiptName, String sid) {
    //     String url = String.format("%s/api/resource/Purchase Receipt/%s?fields=[\"docstatus\"]", erpnextApiUrl, receiptName);
    
    //     HttpHeaders headers = new HttpHeaders();
    //     headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    //     headers.add("Cookie", "sid=" + sid);
    //     headers.set("Authorization", "token " + erpnextApiKey + ":" + erpnextApiSecret);
    
    //     HttpEntity<String> request = new HttpEntity<>(headers);
    
    //     try {
    //         ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, request, Map.class);
    //         Map<String, Object> data = (Map<String, Object>) response.getBody().get("data");
    //         if (data != null) {
    //             Integer docstatus = (Integer) data.get("docstatus");
    //             return docstatus != null && docstatus == 1;
    //         }
    //     } catch (Exception e) {
    //         // Ignorer l'erreur et retourner false
    //     }
    //     return false;
    // }
    


}
