package com.finance.matching.strategy.base;

import com.finance.model.proposal.Proposal;
import com.finance.model.offer.Offer;
import com.finance.model.request.Request;

import java.util.List;

public interface MatchStrategy {
    public Proposal matchRequest(Request inRequest, List<Offer> data);
}
