package com.finance.matching.strategy.base;

import com.finance.model.match.Match;
import com.finance.model.match.MatchStatus;
import com.finance.model.proposal.Proposal;
import com.finance.model.offer.Offer;
import com.finance.model.request.Request;

import java.math.BigDecimal;
import java.util.*;

public class MatchLinearGreedy implements MatchStrategy{

    @Override
    public Proposal matchRequest(Request inRequest, List<Offer> data) {
        List<Match> matches = new ArrayList<Match>();

        //Get the closest amount
        List<Integer> selected = new ArrayList<Integer>();
        BigDecimal target = inRequest.getRequestedAmount();
        int smallestCandidate = data.size(); //Finish off remainder
        for (int i = 0; i < data.size(); i++) {
            BigDecimal currentAmount = data.get(i).getAmount();

            if (target.compareTo(currentAmount) >= 0) {
                target = target.subtract(currentAmount);
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
            matches.add(
                    new Match(
                            null,
                            data.get(index),
                            data.get(index).getAmount(),
                            MatchStatus.created
                    )
            );
        }

        //Cover the leftover with the smallest candidate
        //Unless every offer was used up
        if (smallestCandidate < data.size()) {
            matches.add(
                    new Match(
                            null,
                            data.get(smallestCandidate),
                            target,
                            MatchStatus.created
                    )
            );
        }

        return new Proposal(null, inRequest, matches);
    }
}
