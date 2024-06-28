package com.finance.dto;

import com.finance.model.offer.Offer;
import com.finance.model.offer.OfferStatus;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OfferDTO {
    private Long id = null;
    private Long lender_id;
    private BigDecimal amount;
    private BigDecimal interest_rate;
    private OfferStatus status;
    private Long duration_days;

    public Offer toOffer() {
        return new Offer(id, lender_id, amount, interest_rate, status, duration_days);
    }
}
