package com.finance.dto.response;

import com.finance.model.offer.OfferStatus;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OfferFullDTO {
    private Long offerId;
    private UserDTO lender;
    private BigDecimal amount;
    private BigDecimal interestRate;
    private OfferStatus status;
    private Long durationDays;
}
