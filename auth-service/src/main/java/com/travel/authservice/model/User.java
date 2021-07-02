package com.travel.authservice.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.UUID;

@Document
@Data
public class User {
    @Id
    private String id;
    private String username;
    private String password;
    private String email;
    private String country;
    private List<Role> roleList;
}
