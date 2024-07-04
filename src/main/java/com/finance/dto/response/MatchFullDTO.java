package com.finance.dto.response;

import com.finance.model.match.MatchStatus;
import com.finance.model.offer.Offer;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class MatchFullDTO {
    private Long matchId;
    private Offer offer;
    private BigDecimal amount;
    private MatchStatus status;
}
