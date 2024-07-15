package com.finance.dto.request;

import com.finance.model.proposal.ProposalStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProposalStatusDTO {
    private ProposalStatus status;
}
