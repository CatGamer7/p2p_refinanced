package com.finance.dto.request;

import com.finance.model.match.MatchStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatchStatusDTO {
    private MatchStatus status;
}
