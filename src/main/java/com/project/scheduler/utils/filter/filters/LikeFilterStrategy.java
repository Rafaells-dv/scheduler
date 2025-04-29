package com.project.scheduler.utils.filter.filters;

import com.project.scheduler.utils.filter.FilterStrategy;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LikeFilterStrategy<T> extends FilterStrategy<T> {

    @Override
    public Specification<T> buildSpecification(Map<String, Object> filter) {
        return (Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            for (Map.Entry<String, Object> entry : filter.entrySet()) {
                Object value = entry.getValue();
                if (!(value instanceof String) || value == null) continue;

                String key = entry.getKey();
                boolean isMin = key.endsWith("-min");
                boolean isMax = key.endsWith("-max");

                String baseKey = key;
                if (isMin || isMax) {
                    baseKey = key.substring(0, key.length() - 4);
                }

                String normalizedField = fieldNormalization(baseKey);

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



