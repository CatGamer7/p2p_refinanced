package com.finance.dto.response;

import com.finance.model.match.MatchStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class MatchFullDTO {
    private Long matchId;
    private OfferFullDTO offer;
    private BigDecimal amount;
    private MatchStatus status;
    private LocalDateTime createdTimestamp;
}
