package com.finance.matching.strategy.offerBased;

import com.finance.matching.strategy.base.MatchStrategy;
import com.finance.model.match.Match;
import com.finance.model.match.MatchStatus;
import com.finance.model.offer.Offer;
import com.finance.model.proposal.Proposal;
import com.finance.model.proposal.ProposalStatus;
import com.finance.model.request.Request;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public abstract class OfferBasedLinearGreedy implements MatchStrategy {
    public Proposal matchRequest(Request inRequest, List<Offer> data) {
        Proposal out = new Proposal(null, inRequest, ProposalStatus.created, null, null);
        List<Match> matches = new ArrayList<Match>();

        //Get the closest amount
        List<Offer> selected = new ArrayList<Offer>();
        BigDecimal target = inRequest.getRequestedAmount();
        Offer smallestCandidate = null;
        for (int i = 0; i < data.size(); i++) {
            Offer current = data.get(i);

            if (target.compareTo(current.getAmount()) >= 0) {

                //Prevent selecting lower amount with worse conditions
                if ((smallestCandidate != null) && (!smallestCandidateCriteria(current, smallestCandidate))) {
                    continue;
                }

                target = target.subtract(current.getAmount());
                selected.add(current);
                smallestCandidate = null; //Prevent having selected i as candidate

                if (target.compareTo(BigDecimal.ZERO) == 0) {
                    smallestCandidate = null; //Disable remainder completion
                    break;
                }
            }
            else if (smallestCandidate == null) {
                smallestCandidate = current;
            }
            else if (smallestCandidateCriteria(current, smallestCandidate)) {
                smallestCandidate = current;
            }
        }

        for (Offer o : selected) {
            matches.add(
                    new Match(
                            null,
                            o,
                            o.getAmount(),
                            MatchStatus.created,
                            out,
                            null
                    )
            );
        }

        //Cover the leftover with the smallest candidate
        //Unless every offer was used up
        if (smallestCandidate != null) {
            matches.add(
                    new Match(
                            null,
                            smallestCandidate,
                            target,
                            MatchStatus.created,
                            out,
                            null
                    )
            );
        }

        out.setMatches(matches);

        return out;
    };

    protected abstract boolean smallestCandidateCriteria(Offer current, Offer recorded);
}
