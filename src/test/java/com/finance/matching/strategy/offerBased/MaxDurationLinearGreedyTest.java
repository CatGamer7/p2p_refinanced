package com.finance.matching.strategy.offerBased;

import com.finance.matching.strategy.TestCaseHelper;
import com.finance.matching.strategy.base.MatchStrategy;
import com.finance.model.match.Match;
import com.finance.model.offer.Offer;
import com.finance.model.offer.OfferStatus;
import com.finance.model.request.Request;
import com.finance.model.request.RequestStatus;
import com.finance.model.user.User;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

class MaxDurationLinearGreedyTest extends TestCaseHelper {

    @Test
    void matchRequest() {
        List<Offer> data;
        Request req;
        List<Long> solution;
        List<Match> result;
        User testUser = new User();

        //Case 1
        data = Arrays.asList(new Offer[]{
                new Offer(0L, testUser, BigDecimal.valueOf(30000), BigDecimal.valueOf(5), OfferStatus.available, 91L, null, null),
                new Offer(1L, testUser, BigDecimal.valueOf(20000), BigDecimal.valueOf(5), OfferStatus.available, 91L, null, null),
                new Offer(2L, testUser, BigDecimal.valueOf(10000), BigDecimal.valueOf(5), OfferStatus.available, 91L, null, null),
                new Offer(3L, testUser, BigDecimal.valueOf(10000), BigDecimal.valueOf(5), OfferStatus.available, 61L, null, null),
                new Offer(4L, testUser, BigDecimal.valueOf(10000), BigDecimal.valueOf(5), OfferStatus.available, 30L, null, null),
        });
        req = new Request(0L, testUser, BigDecimal.valueOf(60000), "", RequestStatus.pending, null, null);
        solution = Arrays.asList(new Long[]{0L, 1L, 2L});
        testCaseHelper(data, req, solution);

        //Case 2
        data = Arrays.asList(new Offer[]{
                new Offer(0L, testUser, BigDecimal.valueOf(30000), BigDecimal.valueOf(5), OfferStatus.available, 91L, null, null),
                new Offer(1L, testUser, BigDecimal.valueOf(20000), BigDecimal.valueOf(5), OfferStatus.available, 61L, null, null),
                new Offer(2L, testUser, BigDecimal.valueOf(10000), BigDecimal.valueOf(5), OfferStatus.available, 61L, null, null),
                new Offer(3L, testUser, BigDecimal.valueOf(10000), BigDecimal.valueOf(5), OfferStatus.available, 61L, null, null),
                new Offer(4L, testUser, BigDecimal.valueOf(10000), BigDecimal.valueOf(5), OfferStatus.available, 30L, null, null),
        });
        req = new Request(0L, testUser, BigDecimal.valueOf(20000), "", RequestStatus.pending, null, null);
        solution = Arrays.asList(new Long[]{0L});
        testCaseHelper(data, req, solution);
    }

    @Override
    protected MatchStrategy getStrategy() {
        return new MaxDurationLinearGreedy();
    }
}