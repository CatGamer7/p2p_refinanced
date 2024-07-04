package com.finance.service.match;

import com.finance.dto.request.FilterDTO;
import com.finance.model.match.Match;
import com.finance.model.offer.Offer;
import com.finance.service.SpecificationHelper;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Join;
import org.springframework.stereotype.Component;

@Component
public class MatchSpecification extends SpecificationHelper<Match> {

    @Override
    protected Specification<Match> filterToSpecification(FilterDTO filter) {
        switch (filter.getOperator().toLowerCase()){

            case "=":
                return (root, query, criteriaBuilder) -> {
                    Join<Offer, Match> expandedMatch = root.join("offer");
                    return criteriaBuilder.equal(expandedMatch.get(filter.getColumn()),
                            castToType(expandedMatch.get(filter.getColumn()).getJavaType(),
                                    filter.getOperands().getFirst()));
                };

            case ">=":
                return (root, query, criteriaBuilder) -> {
                    Join<Offer, Match> expandedMatch = root.join("offer");
                    return criteriaBuilder.ge(expandedMatch.get(filter.getColumn()),
                            castToNumber(expandedMatch.get(filter.getColumn()).getJavaType(),
                                    filter.getOperands().getFirst()));
                };

            case "<=":
                return (root, query, criteriaBuilder) -> {
                    Join<Offer, Match> expandedMatch = root.join("offer");
                    return criteriaBuilder.le(expandedMatch.get(filter.getColumn()),
                            castToNumber(expandedMatch.get(filter.getColumn()).getJavaType(),
                                    filter.getOperands().getFirst()));
                };

            default:
                throw new IllegalArgumentException("Operator not supported");
        }
    }

}
