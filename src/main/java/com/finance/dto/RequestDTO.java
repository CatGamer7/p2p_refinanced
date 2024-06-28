package com.finance.dto;

import com.finance.model.request.Request;
import com.finance.model.request.RequestStatus;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class RequestDTO {
    private Long id = null;
    private Long borrower_id;
    private BigDecimal requested_amount;
    private String reason;
    private RequestStatus status;

    public Request toOffer() {
        return new Request(id, borrower_id, requested_amount, reason, status);
    }
}
