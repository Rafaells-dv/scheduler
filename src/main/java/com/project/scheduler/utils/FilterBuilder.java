package com.project.scheduler.utils;

import com.project.scheduler.dto.filters.PageFilterDTO;
import com.project.scheduler.strategies.filters.DefaultFilterStrategy;
import com.project.scheduler.strategies.filters.LikeFilterStrategy;
import com.project.scheduler.strategies.filters.RangeFilterStrategy;
import com.project.scheduler.strategies.interfaces.FilterStrategy;
import org.springframework.data.jpa.domain.Specification;

public class FilterBuilder<T> {

    private FilterStrategy<T> filterStrategy;
    private PageFilterDTO filter;

    public FilterBuilder(PageFilterDTO filter) {
        if (hasRangeFilter(filter)) {
            filterStrategy = new RangeFilterStrategy<>();
            this.filter = filter;
        } else if (hasLikeFilter(filter)) {
            filterStrategy = new LikeFilterStrategy<>();
            this.filter = filter;
        } else {
            filterStrategy = new DefaultFilterStrategy<>();
            this.filter = filter;
        }
    }

    public Specification<T> buildSpecification() {
        return filterStrategy.buildSpecification(filter);
    }

    private boolean hasRangeFilter(PageFilterDTO filter) {
        // Verifica se algum filtro é de intervalo (por exemplo, 'fieldMin' ou 'fieldMax')
        return filter.getFilters().keySet().stream().anyMatch(key -> key.endsWith("Min") || key.endsWith("Max"));
    }

    private boolean hasLikeFilter(PageFilterDTO filter) {
        // Verifica se algum filtro é de like (campo com string)
        return filter.getFilters().values().stream().anyMatch(value -> value instanceof String);
    }
}

