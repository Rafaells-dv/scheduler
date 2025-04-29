package com.project.scheduler.utils;

import com.project.scheduler.composites.CompositeFilterStrategy;
import com.project.scheduler.strategies.filters.DefaultFilterStrategy;
import com.project.scheduler.strategies.filters.LikeFilterStrategy;
import com.project.scheduler.strategies.filters.RangeFilterStrategy;
import com.project.scheduler.strategies.interfaces.FilterStrategy;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FilterBuilder<T> {

    private Map<String, Object> filter;

    public FilterBuilder(Map<String, Object> filter) {
        this.filter = filter;
    }

    public Specification<T> buildSpecification() {
        List<FilterStrategy<T>> strategies = new ArrayList<>();

        filter = convertTypes(filter);

        if(hasRangeFilter(filter)) {
            strategies.add(new RangeFilterStrategy());
        }

        if(hasLikeFilter(filter)) {
            strategies.add(new LikeFilterStrategy());
        }

        if(hasExactFilter(filter)) {
            strategies.add(new DefaultFilterStrategy());
        }

        return new CompositeFilterStrategy<>(strategies).buildSpecification(this.filter);
    }

    private Map<String, Object> convertTypes(Map<String, Object> filters) {
        Map<String, Object> converted = new HashMap<>();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

        for (Map.Entry<String, Object> entry : filters.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            if (value instanceof String strValue) {
                Object newValue = strValue;

                try {
                    if (key.toLowerCase().contains("date")) {
                        if (strValue.contains("T")) {
                            newValue = LocalDateTime.parse(strValue, dateTimeFormatter);
                        } else {
                            newValue = LocalDate.parse(strValue, dateFormatter).atStartOfDay();
                        }
                    } else if (key.toLowerCase().contains("id")) {
                        newValue = Long.parseLong(strValue);
                    } else if (strValue.matches("\\d+")) {
                        newValue = Integer.parseInt(strValue);
                    }
                } catch (Exception e) {
                    System.out.println("Error converting " + key + " to " + value + "\n Erro:" + e.getMessage());
                }

                converted.put(key, newValue);
            } else {
                converted.put(key, value);
            }
        }

        return converted;
    }

    private boolean hasRangeFilter(Map<String, Object> filter) {
        return filter.keySet().stream().anyMatch(key -> key.endsWith("Min") || key.endsWith("Max"));
    }

    private boolean hasLikeFilter(Map<String, Object> filter) {
        return filter.values().stream().anyMatch(value -> value instanceof String);
    }

    private boolean hasExactFilter(Map<String, Object> filter) {
        return filter.entrySet().stream()
                .anyMatch(entry -> {
                    String key = entry.getKey();
                    Object value = entry.getValue();
                    return value != null && !(value instanceof String) &&
                            !key.endsWith("Min") && !key.endsWith("Max");
                });
    }
}

