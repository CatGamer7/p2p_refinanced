package com.finance.dto.response;

import com.finance.model.match.MatchStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatchFullDTO {
    private Long matchId;
    private OfferFullDTO offer;
    private BigDecimal amount;
    private MatchStatus status;
    private LocalDateTime createdTimestamp;
    private PrposalBriefDTO proposal;
}
