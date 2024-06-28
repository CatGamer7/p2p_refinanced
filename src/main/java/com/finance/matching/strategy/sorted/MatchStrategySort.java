package com.finance.matching.strategy.sorted;

import com.finance.model.proposal.Proposal;
import com.finance.matching.strategy.base.MatchStrategy;
import com.finance.model.request.Request;

public interface MatchStrategySort {
    public Proposal matchRequest(Request inRequest);
    public MatchStrategy getStrategy();
}
