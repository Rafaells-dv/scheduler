package com.project.scheduler.strategies.filters;

import com.project.scheduler.strategies.interfaces.FilterStrategy;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DefaultFilterStrategy<T> implements FilterStrategy<T> {

    @Override
    public Specification<T> buildSpecification(Map<String, Object> filter) {
        return (Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            for (Map.Entry<String, Object> entry : filter.entrySet()) {
                Object value = entry.getValue();
                if (value == null) continue;

                String normalizedField = entry.getKey().replace("-", ".");

                Path<?> path = root;
                if (normalizedField.contains(".")) {
                    for (String part : normalizedField.split("\\.")) {
                        path = path.get(part);
                    }
                } else {
                    path = path.get(normalizedField);
                }

                predicates.add(cb.equal(path, value));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}

