package com.finance.matching.strategy.sorted;

import com.finance.matching.Proposal;
import com.finance.matching.strategy.base.MatchLinearGreedy;
import com.finance.matching.strategy.base.MatchStrategy;
import com.finance.offer.Offer;
import com.finance.request.Request;

import java.util.List;

public class MatchStrategyMinOffers implements MatchStrategySort {

    @Override
    public Proposal matchRequest(Request inRequest) {

        //Get data
        List<Offer> amountSorted = List.<Offer>of(); // where <= target and status | desc(am) | segmented

        MatchStrategy strategy = getStrategy();
        return strategy.matchRequest(inRequest, amountSorted);
    }

    @Override
    public MatchStrategy getStrategy() {
        return new MatchLinearGreedy();
    }
}
