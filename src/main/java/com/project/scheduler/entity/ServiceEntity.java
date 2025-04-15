package com.project.scheduler.entity;

import com.project.scheduler.config.DurationToStringConverter;
import jakarta.persistence.*;
import lombok.*;

import java.time.Duration;

@Entity(name = "service")
@Table(name = "service")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ServiceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;
    private String description;
    private Double price;

    @Column(name = "duration")
    @Convert(converter = DurationToStringConverter.class)
    private Duration duration;
    private Boolean active;

    public static ServiceEntity create(String title, String description, Double price, Duration duration) {
        return new ServiceEntity(null, title, description, price, duration, Boolean.TRUE);
    }

    public void update(String title, String description, Double price, Duration duration) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.duration = duration;
    }

    public void activate() {
        this.active = true;
    }

    public void inactivate() {
        this.active = false;
    }
}
