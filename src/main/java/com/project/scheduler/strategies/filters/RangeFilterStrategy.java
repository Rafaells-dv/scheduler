package com.project.scheduler.strategies.filters;

import com.project.scheduler.dto.filters.PageFilterDTO;
import com.project.scheduler.strategies.interfaces.FilterStrategy;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RangeFilterStrategy<T> implements FilterStrategy<T> {

    @Override
    public Specification<T> buildSpecification(PageFilterDTO filter) {
        return (Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            for (Map.Entry<String, Object> entry : filter.getFilters().entrySet()) {
                String field = entry.getKey();
                Object value = entry.getValue();

                if (value != null) {
                    if (field.endsWith("Min")) {
                        String fieldName = field.substring(0, field.length() - 3); // Remove "Min"
                        predicates.add(cb.greaterThanOrEqualTo(root.get(fieldName), (Comparable) value));
                    } else if (field.endsWith("Max")) {
                        String fieldName = field.substring(0, field.length() - 3); // Remove "Max"
                        predicates.add(cb.lessThanOrEqualTo(root.get(fieldName), (Comparable) value));
                    }
                }
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
