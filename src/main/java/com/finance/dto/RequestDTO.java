package com.finance.dto;

import com.finance.model.request.RequestStatus;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class RequestDTO {
    private Long requestId;
    private Long borrowerId;
    private BigDecimal requestedAmount;
    private String reason;
    private RequestStatus status;
}
