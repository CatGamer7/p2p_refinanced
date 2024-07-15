package com.finance.dto.response;

import com.finance.model.proposal.ProposalStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProposalFullDTO {
    private Long proposalId;
    private RequestFullDTO request;
    private ProposalStatus status;
    private List<MatchFullDTO> matches;
    private LocalDateTime createdTimestamp;
}
