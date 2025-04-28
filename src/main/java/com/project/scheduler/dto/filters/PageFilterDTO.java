package com.project.scheduler.dto.filters;

import com.project.scheduler.enums.EnOrderDirection;
import lombok.*;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PageFilterDTO {
    private Map<String, Object> filters;
}
