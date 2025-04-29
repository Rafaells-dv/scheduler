package com.project.scheduler.utils.filter;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CompositeFilterStrategy<T> extends FilterStrategy<T> {

    private final List<FilterStrategy<T>> strategies;

    public CompositeFilterStrategy(List<FilterStrategy<T>> strategies) {
        this.strategies = strategies;
    }


    @Override
    public Specification<T> buildSpecification(Map<String, Object> pageFilterDTO) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            for (FilterStrategy<T> strategy : strategies) {
                Predicate predicate = strategy.buildSpecification(pageFilterDTO).toPredicate(root, query, cb);
                if (predicate != null) {
                    predicates.add(predicate);
                }
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
