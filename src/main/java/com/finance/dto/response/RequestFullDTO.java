package com.finance.dto.response;

import com.finance.model.request.RequestStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class RequestFullDTO {
    private Long requestId;
    private UserDTO borrower;
    private BigDecimal requestedAmount;
    private String reason;
    private RequestStatus status;
    private LocalDateTime createdTimestamp;
}
