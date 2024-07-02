package com.finance.dto;

import com.finance.model.offer.OfferStatus;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OfferDTO {
    private Long offerId;
    private Long lenderId;
    private BigDecimal amount;
    private BigDecimal interestRate;
    private OfferStatus status;
    private Long durationDays;
}
