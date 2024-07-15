package com.finance.dto.response;

import com.finance.model.offer.OfferStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OfferFullDTO {
    private Long offerId;
    private UserDTO lender;
    private BigDecimal amount;
    private BigDecimal interestRate;
    private OfferStatus status;
    private Long durationDays;
    private LocalDateTime createdTimestamp;
}
