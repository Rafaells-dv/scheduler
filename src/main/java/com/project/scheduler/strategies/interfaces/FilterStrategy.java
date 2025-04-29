package com.project.scheduler.strategies.interfaces;

import org.springframework.data.jpa.domain.Specification;

import java.util.Map;

public interface FilterStrategy<T> {
    Specification<T> buildSpecification(Map<String, Object> filter);
}
