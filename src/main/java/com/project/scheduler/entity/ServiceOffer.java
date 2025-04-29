package com.project.scheduler.entity;

import com.project.scheduler.config.DurationToStringConverter;
import jakarta.persistence.*;
import lombok.*;
import java.time.Duration;

@Entity
@Table(name = "service_offer")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ServiceOffer {
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

    private ServiceOffer(String title, String description, Double price, Duration duration, Boolean active) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.active = active;
    }

    public static ServiceOffer create(String title, String description, Double price, Duration duration) {
        return new ServiceOffer(title, description, price, duration, Boolean.TRUE);
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
