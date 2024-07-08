package com.finance.matching.strategy.offerBased;

import com.finance.model.offer.Offer;

public class MinDurationLinearGreedy extends OfferBasedLinearGreedy {

    @Override
    protected boolean smallestCandidateCriteria(Offer current, Offer recorded) {
        return (current.getAmount().compareTo(recorded.getAmount()) < 0)
                && (current.getDurationDays().compareTo(recorded.getDurationDays()) <= 0);
    }
}
