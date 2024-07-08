package com.finance.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserFullDTO {
    private Long userId;
    private String name;
    private String email;
    private String passwordDigest;
    private boolean staff;
    private boolean active;
}
