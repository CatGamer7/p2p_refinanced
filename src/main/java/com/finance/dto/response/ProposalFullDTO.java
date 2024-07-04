package com.finance.dto.response;

import com.finance.model.match.Match;
import com.finance.model.proposal.ProposalStatus;
import com.finance.model.request.Request;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
public class ProposalFullDTO {
    private Long proposalId;
    private Request request;
    private ProposalStatus status;
    private List<Match> matches;
}
