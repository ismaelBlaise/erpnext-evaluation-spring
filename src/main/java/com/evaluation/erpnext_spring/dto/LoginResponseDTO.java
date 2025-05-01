package com.evaluation.erpnext_spring.dto;

import lombok.Data;

@Data
public class LoginResponseDTO {
    private boolean success;
    private String message;
    private String sessionId;
    private String fullName;


}