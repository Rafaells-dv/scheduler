package com.project.scheduler.strategies.filters;

import com.project.scheduler.strategies.interfaces.FilterStrategy;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RangeFilterStrategy<T> implements FilterStrategy<T> {

    @Override
    public Specification<T> buildSpecification(Map<String, Object> filter) {
        return (Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            for (Map.Entry<String, Object> entry : filter.entrySet()) {
                String rawField = entry.getKey();
                Object value = entry.getValue();

                if (value == null) continue;

                String normalizedField = rawField.replace("-", ".");
                String fieldName = normalizedField;
                boolean isMin = false;
                boolean isMax = false;

                if (normalizedField.endsWith("Min")) {
                    fieldName = normalizedField.substring(0, normalizedField.length() - 3);
                    isMin = true;
                } else if (normalizedField.endsWith("Max")) {
                    fieldName = normalizedField.substring(0, normalizedField.length() - 3);
                    isMax = true;
                }

                Path<? extends Comparable> path;
                if (fieldName.contains(".")) {
                    String[] parts = fieldName.split("\\.");
                    Path<?> nestedPath = root;
                    for (String part : parts) {
                        nestedPath = nestedPath.get(part);
                    }
                    path = (Path<? extends Comparable>) nestedPath;
                } else {
                    path = root.get(fieldName);
                }

                if (isMin) {
                    predicates.add(cb.greaterThanOrEqualTo(path, (Comparable) value));
                } else if (isMax) {
                    predicates.add(cb.lessThanOrEqualTo(path, (Comparable) value));
                }
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}

