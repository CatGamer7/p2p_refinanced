package com.finance.dto.response;

import com.finance.model.proposal.ProposalStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrposalBriefDTO {
    private Long proposalId;
    private ProposalStatus status;
}
