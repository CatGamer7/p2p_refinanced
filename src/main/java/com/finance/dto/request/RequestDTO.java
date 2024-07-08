package com.finance.dto.request;

import com.finance.model.request.RequestStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestDTO {
    private Long requestId;
    private Long borrowerId;
    private BigDecimal requestedAmount;
    private String reason;
    private RequestStatus status;
}
