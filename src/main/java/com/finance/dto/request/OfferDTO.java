package com.finance.dto.request;

import com.finance.model.offer.OfferStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OfferDTO {
    private Long offerId;
    private Long lenderId;
    private BigDecimal amount;
    private BigDecimal interestRate;
    private OfferStatus status;
    private Long durationDays;
}
