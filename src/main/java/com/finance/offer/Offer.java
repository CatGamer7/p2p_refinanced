package com.finance.offer;

import java.math.BigDecimal;

public class Offer {
    public enum offerStatus{
        available, matched
    }

    public Offer(Long offer_id, Long lender_id, BigDecimal amount, BigDecimal interest_rate, int status, Long duration_days) {
        this.offer_id = offer_id;
        this.lender_id = lender_id;
        this.amount = amount;
        this.interest_rate = interest_rate;
        this.status = status;
        this.duration_days = duration_days;
    }

    public Long offer_id;
    public Long lender_id;
    public BigDecimal amount;
    public BigDecimal interest_rate;
    public int status;
    public Long duration_days;
}
