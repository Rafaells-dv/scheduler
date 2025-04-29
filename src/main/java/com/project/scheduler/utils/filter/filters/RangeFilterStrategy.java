package com.project.scheduler.utils.filter.filters;

import com.project.scheduler.utils.filter.FilterStrategy;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RangeFilterStrategy<T> extends FilterStrategy<T> {
    private static final Set<String> SUPPORTED_FIELDS = Set.of("dateSched", "price", "duration");

    @Override
    public Specification<T> buildSpecification(Map<String, Object> filter) {
        return (Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            for (Map.Entry<String, Object> entry : filter.entrySet()) {
                Object value = entry.getValue();
                if (value == null) continue;

                boolean isMin = entry.getKey().endsWith("-min");
                boolean isMax = entry.getKey().endsWith("-max");

                String baseFieldKey = entry.getKey();
                if (isMin) {
                    baseFieldKey = baseFieldKey.substring(0, baseFieldKey.length() - 4);
                } else if (isMax) {
                    baseFieldKey = baseFieldKey.substring(0, baseFieldKey.length() - 4);
                }

                String fieldName = fieldNormalization(baseFieldKey);
                if (!SUPPORTED_FIELDS.contains(fieldName)) continue;

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

    @SuppressWarnings("unchecked")
    private Path<? extends Comparable> resolvePath(Root<T> root, String fieldName) {
        Path<?> path = root;
        for (String part : fieldName.split("\\.")) {
            path = path.get(part);
        }
        return (Path<? extends Comparable>) path;
    }

    private Object tryConvertValue(Object value, Path<?> path) {
        if (value instanceof String) {
            Class<?> targetType = path.getJavaType();

            try {
                if (targetType.equals(LocalDateTime.class)) {
                    return LocalDateTime.parse((String) value); // ISO format
                }
                // Adicione aqui outras conversões se necessário (ex: LocalDate, BigDecimal, etc)
            } catch (Exception e) {
                System.err.printf("Erro ao converter valor '%s' para %s%n", value, targetType.getSimpleName());
                return null;
            }
        }
        return value;
    }
}



