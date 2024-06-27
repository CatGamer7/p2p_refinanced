package com.finance.request;

import java.math.BigDecimal;

public class Request {
    public enum requestStatus {
        pending, matched, approved
    }

    public Request(Long request_id, Long borrower_id, BigDecimal requested_amount, String reason, int status) {
        this.request_id = request_id;
        this.borrower_id = borrower_id;
        this.requested_amount = requested_amount;
        this.reason = reason;
        this.status = status;
    }

    public Long request_id;
    public Long borrower_id;
    public BigDecimal requested_amount;
    public String reason;
    public int status;
}
