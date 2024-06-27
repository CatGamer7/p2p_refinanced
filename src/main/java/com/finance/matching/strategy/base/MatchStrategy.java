package com.finance.matching.strategy.base;

import com.finance.matching.Proposal;
import com.finance.offer.Offer;
import com.finance.request.Request;

import java.util.List;

public interface MatchStrategy {
    public Proposal matchRequest(Request inRequest, List<Offer> data);
}
