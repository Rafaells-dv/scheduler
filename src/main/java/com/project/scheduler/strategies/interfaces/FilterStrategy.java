package com.project.scheduler.strategies.interfaces;

import com.project.scheduler.dto.filters.PageFilterDTO;
import org.springframework.data.jpa.domain.Specification;

public interface FilterStrategy<T> {
    Specification<T> buildSpecification(PageFilterDTO pageFilterDTO);
}
