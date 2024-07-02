package com.finance.dto;

import lombok.Data;

@Data
public class UserDTO {
    private Long userId;
    private String name;
    private String email;
    private boolean staff;
    private boolean active;
}
