package com.finance.dto.request;

import com.finance.model.match.MatchStatus;
import lombok.Data;

@Data
public class MatchStatusDTO {
    private MatchStatus status;
}
