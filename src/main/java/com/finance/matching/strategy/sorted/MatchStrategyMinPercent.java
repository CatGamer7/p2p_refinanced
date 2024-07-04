package com.finance.matching.strategy.sorted;

import com.finance.model.proposal.Proposal;
import com.finance.matching.strategy.base.MatchStrategy;
import com.finance.model.offer.Offer;
import com.finance.model.request.Request;
import com.finance.service.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.Arrays;
import java.util.List;

import static org.springframework.data.jpa.domain.Specification.where;

public class MatchStrategyMinPercent extends AbstractMatchStrategySort {

    @Autowired
    protected OfferService service;

    @Override
    protected List<Offer> getData(Request inRequest) {
        Specification<Offer> spec = where(leTargetAndStatus(inRequest.getRequestedAmount()));
        List<Sort.Order> orders = Arrays.asList(new Sort.Order[] {
                new Sort.Order(Sort.Direction.ASC, "interestRate"),
                new Sort.Order(Sort.Direction.DESC, "amount")
        });

        return service.list(spec, orders);
    }
}
