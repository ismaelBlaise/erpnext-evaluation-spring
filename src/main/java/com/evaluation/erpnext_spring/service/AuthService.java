package com.evaluation.erpnext_spring.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import java.util.Collections;
import com.evaluation.erpnext_spring.dto.ERPNextAuthResponse;
import com.evaluation.erpnext_spring.dto.LoginRequestDTO;
import com.evaluation.erpnext_spring.dto.LoginResponseDTO;

@Service
public class AuthService {

    private final RestTemplate restTemplate;

    @Value("${erpnext.api.url}")
    private String erpnextApiUrl;

    public AuthService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @SuppressWarnings("null")
    public LoginResponseDTO login(LoginRequestDTO loginRequest) {
        String loginUrl = erpnextApiUrl + "/api/method/login";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        // Create JSON request body
        String requestBody = String.format("{\"usr\":\"%s\",\"pwd\":\"%s\"}", 
            loginRequest.getUsername(), 
            loginRequest.getPassword());

        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<ERPNextAuthResponse> response = restTemplate.postForEntity(
                loginUrl, 
                request, 
                ERPNextAuthResponse.class
            );

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                ERPNextAuthResponse authResponse = response.getBody();
                return new LoginResponseDTO(
                    true, 
                    "Login successful", 
                    authResponse.getSid(), 
                    authResponse.getFullName()
                );
            }
        } catch (HttpClientErrorException e) {
            // if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
            //     return new LoginResponseDTO(false, "Invalid username or password");
            // }
            return new LoginResponseDTO(false, "Login failed: " + e.getMessage());
        } catch (Exception e) {
            return new LoginResponseDTO(false, "An error occurred during login: " + e.getMessage());
        }

        return new LoginResponseDTO(false, "Login failed with unknown error");
    }
}