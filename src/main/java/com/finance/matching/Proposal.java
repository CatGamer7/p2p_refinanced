package com.finance.matching;

import java.util.List;

public class Proposal {
    public Proposal(Long proposal_id, List<Match> matches) {
        this.proposal_id = proposal_id;
        this.matches = matches;
    }

    public Long getProposal_id() {
        return proposal_id;
    }

    public void setProposal_id(Long proposal_id) {
        this.proposal_id = proposal_id;
    }

    public List<Match> getMatches() {
        return matches;
    }

    public void setMatches(List<Match> matches) {
        this.matches = matches;
    }

    private Long proposal_id;
    private List<Match> matches;
}
