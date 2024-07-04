package com.finance.matching.strategy.sorted;

import com.finance.matching.strategy.base.MatchLinearGreedy;
import com.finance.matching.strategy.base.MatchStrategy;
import com.finance.model.offer.Offer;
import com.finance.model.offer.OfferStatus;
import com.finance.model.proposal.Proposal;
import com.finance.model.request.Request;
import com.finance.service.ProposalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.List;


public abstract class AbstractMatchStrategySort {

    @Autowired
    protected ProposalService service;

    public Proposal matchRequest(Request inRequest) {
        MatchStrategy strat =getStrategy();
        List<Offer> data = getData(inRequest);
        Proposal prop = strat.matchRequest(inRequest, data);
        save(prop);

        return prop;
    }

    protected Specification<Offer> leTargetAndStatus(BigDecimal target) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.and(
                        criteriaBuilder.equal(
                                root.get("status"),
                                OfferStatus.available
                        ),
                        criteriaBuilder.le(
                                root.get("amount"),
                                target
                        )
                );
    }

    protected MatchStrategy getStrategy() {
        return new MatchLinearGreedy();
    }

    protected abstract List<Offer> getData(Request inRequest);

    protected void save(Proposal prop) {
        service.save(prop);
    }
}
