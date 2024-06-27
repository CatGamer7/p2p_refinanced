package com.finance.matching.strategy.base;

import com.finance.matching.Match;
import com.finance.matching.Proposal;
import com.finance.offer.Offer;
import com.finance.request.Request;

import java.math.BigDecimal;
import java.util.*;

public class MatchLinearGreedy implements MatchStrategy{

    @Override
    public Proposal matchRequest(Request inRequest, List<Offer> data) {
        List<Match> out = new ArrayList<Match>();

        //Get the closest amount
        List<Integer> selected = new ArrayList<Integer>();
        BigDecimal target = inRequest.requested_amount;
        int smallestCandidate = data.size(); //Finish off remainder
        for (int i = 0; i < data.size(); i++) {
            BigDecimal curentAmount = data.get(i).amount;

            if (target.compareTo(curentAmount) >= 0) {
                target = target.subtract(curentAmount);
                selected.add(i);
                smallestCandidate = i + 1; //Prevent having selected i as candidate

                if (target.compareTo(BigDecimal.ZERO) == 0) {
                    smallestCandidate = data.size(); //Disable remainder completion
                    break;
                }
            }
            else {
                smallestCandidate = i;
            }
        }

        for (int index : selected) {
            //Update offer status

            out.add(
                    new Match(
                            0L,
                            inRequest.request_id,
                            data.get(index).offer_id,
                            data.get(index).amount,
                            0
                    )
            );
        }

        //Cover the leftover with the smallest candidate
        //Unless every offer was used up
        if (smallestCandidate < data.size()) {
            out.add(
                    new Match(
                            0L,
                            inRequest.request_id,
                            data.get(smallestCandidate).offer_id,
                            target,
                            0
                    )
            );
        }

        //Save matches to db
        for (Match m : out) {
            //
        }

        return new Proposal(0L, out);
    }
}
