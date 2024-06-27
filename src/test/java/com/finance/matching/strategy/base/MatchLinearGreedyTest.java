package com.finance.matching.strategy.base;

import com.finance.matching.Match;
import com.finance.offer.Offer;
import com.finance.request.Request;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MatchLinearGreedyTest {

    private MatchLinearGreedy strategy = new MatchLinearGreedy();

    @Test
    void matchRequest() {
        List<Offer> data;
        Request req;
        List<Long> solution;
        List<Match> result;

        //Case 1: exact min offers
        data = Arrays.asList(new Offer[] {
                new Offer(0L, 0L, BigDecimal.valueOf(40000), BigDecimal.valueOf(5), 0, 91L),
                new Offer(1L, 1L, BigDecimal.valueOf(20000), BigDecimal.valueOf(5), 0, 91L),
                new Offer(2L, 2L, BigDecimal.valueOf(10000), BigDecimal.valueOf(5), 0, 91L),
                new Offer(3L, 3L, BigDecimal.valueOf(10000), BigDecimal.valueOf(5), 0, 91L),
                new Offer(4L, 4L, BigDecimal.valueOf(10000), BigDecimal.valueOf(5), 0, 91L),
        });
        req = new Request(0L, 0L, BigDecimal.valueOf(60000), "", 0);
        solution = Arrays.asList(new Long[]{0L, 1L});
        testCaseHelper(data, req, solution);

        //Case 2: remainder min offers
        data = Arrays.asList(new Offer[] {
                new Offer(0L, 0L, BigDecimal.valueOf(40000), BigDecimal.valueOf(5), 0, 91L),
                new Offer(1L, 1L, BigDecimal.valueOf(20000), BigDecimal.valueOf(5), 0, 91L),
                new Offer(2L, 2L, BigDecimal.valueOf(10000), BigDecimal.valueOf(5), 0, 91L),
                new Offer(3L, 3L, BigDecimal.valueOf(10000), BigDecimal.valueOf(5), 0, 91L),
                new Offer(4L, 4L, BigDecimal.valueOf(10000), BigDecimal.valueOf(5), 0, 91L),
        });
        req = new Request(0L, 0L, BigDecimal.valueOf(65000), "", 0);
        solution = Arrays.asList(new Long[]{0L, 1L, 4L});
        testCaseHelper(data, req, solution);

        //Case 3: min percent
        data = Arrays.asList(new Offer[] {
                new Offer(0L, 0L, BigDecimal.valueOf(20000), BigDecimal.valueOf(3), 0, 91L),
                new Offer(1L, 1L, BigDecimal.valueOf(10000), BigDecimal.valueOf(3), 0, 91L),
                new Offer(2L, 2L, BigDecimal.valueOf(10000), BigDecimal.valueOf(4), 0, 91L),
                new Offer(3L, 3L, BigDecimal.valueOf(30000), BigDecimal.valueOf(5), 0, 91L),
                new Offer(4L, 4L, BigDecimal.valueOf(10000), BigDecimal.valueOf(5), 0, 91L),
        });
        req = new Request(0L, 0L, BigDecimal.valueOf(50000), "", 0);
        solution = Arrays.asList(new Long[]{0L, 1L, 2L, 4L});
        testCaseHelper(data, req, solution);

        //Case 4: min duration
        data = Arrays.asList(new Offer[] {
                new Offer(0L, 0L, BigDecimal.valueOf(10000), BigDecimal.valueOf(5), 0, 30L),
                new Offer(1L, 1L, BigDecimal.valueOf(10000), BigDecimal.valueOf(5), 0, 30L),
                new Offer(2L, 2L, BigDecimal.valueOf(10000), BigDecimal.valueOf(5), 0, 61L),
                new Offer(3L, 3L, BigDecimal.valueOf(20000), BigDecimal.valueOf(5), 0, 91L),
                new Offer(4L, 4L, BigDecimal.valueOf(15000), BigDecimal.valueOf(5), 0, 91L),
        });
        req = new Request(0L, 0L, BigDecimal.valueOf(65000), "", 0);
        solution = Arrays.asList(new Long[]{0L, 1L, 2L, 3L, 4L});
        testCaseHelper(data, req, solution);

        //Case 5: max duration
        data = Arrays.asList(new Offer[] {
                new Offer(0L, 0L, BigDecimal.valueOf(10000), BigDecimal.valueOf(5), 0, 91L),
                new Offer(1L, 1L, BigDecimal.valueOf(5000 ), BigDecimal.valueOf(5), 0, 91L),
                new Offer(2L, 2L, BigDecimal.valueOf(10000), BigDecimal.valueOf(5), 0, 61L),
                new Offer(3L, 3L, BigDecimal.valueOf(35000), BigDecimal.valueOf(5), 0, 30L),
                new Offer(4L, 4L, BigDecimal.valueOf(10000), BigDecimal.valueOf(5), 0, 30L),
        });
        req = new Request(0L, 0L, BigDecimal.valueOf(65000), "", 0);
        solution = Arrays.asList(new Long[]{0L, 1L, 2L, 3L, 4L});
        testCaseHelper(data, req, solution);
    }

    private void testCaseHelper(List<Offer> data, Request req, List<Long> solution) {
        List<Match> result = strategy.matchRequest(req, data).getMatches();

        for (int i = 0; i < result.size(); i++) {
            assertEquals(solution.get(i), result.get(i).offer_id);
        }
    }
}