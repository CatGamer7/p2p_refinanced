package com.finance.service.proposal;

import com.finance.dto.request.FilterDTO;
import com.finance.model.proposal.Proposal;
import com.finance.model.request.Request;
import com.finance.service.SpecificationHelper;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class ProposalSpecification extends SpecificationHelper<Proposal> {

    @Override
    protected Specification<Proposal> filterToSpecification(FilterDTO filter) {
        switch (filter.getOperator().toLowerCase()){

            case "=":
                return (root, query, criteriaBuilder) -> {
                    Join<Request, Proposal> expandedMatch = root.join("request");
                    return criteriaBuilder.equal(expandedMatch.get(filter.getColumn()),
                            castToType(expandedMatch.get(filter.getColumn()).getJavaType(),
                                    filter.getOperands().getFirst()));
                };

            case ">=":
                return (root, query, criteriaBuilder) -> {
                    Join<Request, Proposal> expandedMatch = root.join("request");
                    return criteriaBuilder.ge(expandedMatch.get(filter.getColumn()),
                            castToNumber(expandedMatch.get(filter.getColumn()).getJavaType(),
                                    filter.getOperands().getFirst()));
                };

            case "<=":
                return (root, query, criteriaBuilder) -> {
                    Join<Request, Proposal> expandedMatch = root.join("request");
                    return criteriaBuilder.le(expandedMatch.get(filter.getColumn()),
                            castToNumber(expandedMatch.get(filter.getColumn()).getJavaType(),
                                    filter.getOperands().getFirst()));
                };

            default:
                throw new IllegalArgumentException("Operator not supported");
        }
    }
}
