package com.finance.dto;

import lombok.Data;

@Data
public class UserFullDTO {
    private Long userId;
    private String name;
    private String email;
    private String passwordDigest;
    private boolean staff;
    private boolean active;
}
