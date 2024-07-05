package com.finance.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDTO {
    private Long userId;
    private String name;
    private String email;
    private boolean staff;
    private boolean active;
    private LocalDateTime createdTimestamp;
}
