package com.finance.dto.request;

import com.finance.model.proposal.ProposalStatus;
import lombok.Data;

@Data
public class ProposalStatusDTO {
    private ProposalStatus status;
}
