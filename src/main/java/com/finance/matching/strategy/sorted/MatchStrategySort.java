package com.finance.matching.strategy.sorted;

import com.finance.matching.Proposal;
import com.finance.matching.strategy.base.MatchStrategy;
import com.finance.request.Request;

import java.util.List;

public interface MatchStrategySort {
    public Proposal matchRequest(Request inRequest);
    public MatchStrategy getStrategy();
}
