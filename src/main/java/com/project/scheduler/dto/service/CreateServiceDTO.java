package com.project.scheduler.dto.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Duration;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateServiceDTO {
    private String title;
    private String description;
    private Double price;
    private Duration duration;
}
