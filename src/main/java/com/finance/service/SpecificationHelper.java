package com.finance.service;

import com.finance.dto.request.FilterDTO;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

import static org.springframework.data.jpa.domain.Specification.where;

@Component
public class SpecificationHelper<T> {

    public Specification<T> applyFilters (List<FilterDTO> filters){
        Specification<T> specification = null;

        for (FilterDTO f : filters) {
            if (specification != null) {
                specification = specification.and(filterToSpecification(f));
            }
            else {
                specification = where(filterToSpecification(f));
            }
        }
        return specification;
    }

    protected Specification<T> filterToSpecification(FilterDTO filter) {
        switch (filter.getOperator().toLowerCase()){

            case "=":
                return (root, query, criteriaBuilder) ->
                        criteriaBuilder.equal(root.get(filter.getColumn()),
                                castToType(root.get(filter.getColumn()).getJavaType(),
                                        filter.getOperands().getFirst()));

            case ">=":
                return (root, query, criteriaBuilder) ->
                        criteriaBuilder.ge(root.get(filter.getColumn()),
                                castToNumber(root.get(filter.getColumn()).getJavaType(),
                                        filter.getOperands().getFirst()));

            case "<=":
                return (root, query, criteriaBuilder) ->
                        criteriaBuilder.le(root.get(filter.getColumn()),
                                castToNumber(root.get(filter.getColumn()).getJavaType(),
                                        filter.getOperands().getFirst()));

            default:
                throw new IllegalArgumentException("Operator not supported");
        }
    }

    protected Object castToType(Class<?> fieldType, String value) {
        if (fieldType == BigDecimal.class) {
            return new BigDecimal(value);
        }
        else if (fieldType == String.class) {
            return value;
        }
        else if (fieldType == Long.class) {
            return Long.parseLong(value);
        }
        else if (fieldType == Boolean.class) {
            return Boolean.parseBoolean(value);
        }
        else if (fieldType.isEnum()) {
            return Integer.parseInt(value);
        }
        else {
            throw new IllegalArgumentException("Column type conversion for " + fieldType + " not implemented");
        }
    }

    protected Number castToNumber(Class<?> fieldType, String value) {
        if (fieldType == BigDecimal.class) {
            return new BigDecimal(value);
        }
        else if (fieldType == Long.class) {
            return Long.parseLong(value);
        }
        else if (fieldType.isEnum()) {
            return Integer.parseInt(value);
        }
        else {
            throw new IllegalArgumentException("Column type conversion for " + fieldType + " not implemented");
        }
    }

}
