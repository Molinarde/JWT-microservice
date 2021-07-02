package com.travel.authservice.model.dto;

import lombok.Data;

@Data
public class RegRequest {
    private String username;
    private String password;
    private String email;
    private String country;
}
