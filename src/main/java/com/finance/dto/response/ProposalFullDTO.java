package com.finance.dto.response;

import com.finance.model.proposal.ProposalStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ProposalFullDTO {
    private Long proposalId;
    private RequestFullDTO request;
    private ProposalStatus status;
    private List<MatchFullDTO> matches;
    private LocalDateTime createdTimestamp;
}
