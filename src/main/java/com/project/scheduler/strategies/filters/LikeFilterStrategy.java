package com.project.scheduler.strategies.filters;

import com.project.scheduler.strategies.interfaces.FilterStrategy;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LikeFilterStrategy<T> implements FilterStrategy<T> {

    @Override
    public Specification<T> buildSpecification(Map<String, Object> filter) {
        return (Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            for (Map.Entry<String, Object> entry : filter.entrySet()) {
                Object value = entry.getValue();
                if (!(value instanceof String) || value == null) continue;

                String normalizedField = entry.getKey().replace("-", ".");
                Path<?> path = root;

                for (String part : normalizedField.split("\\.")) {
                    path = path.get(part);
                }

                predicates.add(cb.like(cb.lower(path.as(String.class)), "%" + value.toString().toLowerCase() + "%"));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}



