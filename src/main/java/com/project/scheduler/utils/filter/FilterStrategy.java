package com.project.scheduler.utils.filter;

import com.project.scheduler.utils.NamingUtils;
import org.springframework.data.jpa.domain.Specification;

import java.util.Map;

public abstract class FilterStrategy<T> {
    public abstract Specification<T> buildSpecification(Map<String, Object> filter);

    protected String fieldNormalization(String fieldKey) {
        String normalizedField;

        if(fieldKey.startsWith("client") || fieldKey.startsWith("service")) {
            normalizedField = fieldKey.replace("-", ".");
        } else {
            normalizedField = NamingUtils.kebabToCamel(fieldKey);
        }
        return normalizedField;
    };
}
