package com.finance.matching.strategy.sorted;

import com.finance.matching.strategy.base.MatchLinearGreedy;
import com.finance.matching.strategy.base.MatchStrategy;
import com.finance.model.offer.Offer;
import com.finance.model.request.Request;
import com.finance.service.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

import static org.springframework.data.jpa.domain.Specification.where;

@Component
public class MatchStrategyMinOffers extends AbstractMatchStrategySort {

    @Autowired
    protected OfferService service;

    @Override
    protected MatchStrategy getStrategy() {
        return new MatchLinearGreedy();
    }

    @Override
    protected List<Offer> getData(Request inRequest) {
        Specification<Offer> spec = where(leTargetAndStatus(inRequest.getRequestedAmount()));
        List<Sort.Order> orders = Arrays.asList(new Sort.Order[] {
                new Sort.Order(Sort.Direction.DESC, "amount"),
                new Sort.Order(Sort.Direction.ASC, "createdTimestamp")
        });

        return service.list(spec, orders);
    }
}
