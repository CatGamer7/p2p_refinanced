package com.finance.matching.strategy;

import com.finance.matching.strategy.base.MatchStrategy;
import com.finance.model.match.Match;
import com.finance.model.offer.Offer;
import com.finance.model.request.Request;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class TestCaseHelper {
    protected void testCaseHelper(List<Offer> data, Request req, List<Long> solution) {
        MatchStrategy strategy = getStrategy();
        List<Match> result = strategy.matchRequest(req, data).getMatches();

        for (int i = 0; i < result.size(); i++) {
            assertEquals(solution.get(i), result.get(i).getOffer().getOfferId());
        }
    }

    protected abstract MatchStrategy getStrategy();

}
