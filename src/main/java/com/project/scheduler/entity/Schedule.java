package com.project.scheduler.entity;

import com.project.scheduler.enums.EnSchedStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "schedule",
        uniqueConstraints = @UniqueConstraint(columnNames = {"id_client", "date_sched"})
)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "date_sched")
    private LocalDateTime dateSched;

    @Enumerated(EnumType.STRING)
    private EnSchedStatus status;

    @ManyToOne
    @JoinColumn(name = "id_client")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "id_service")
    private ServiceEntity service;

    public static Schedule create(LocalDateTime dateSched, Client client, ServiceEntity service) {
        return new Schedule(null, dateSched, EnSchedStatus.PENDING, client, service);
    }

    public void markAsPending() {
        this.status = EnSchedStatus.PENDING;
    }

    public void confirm() {
        this.status = EnSchedStatus.CONFIRMED;
    }

    public void cancel() {
        this.status = EnSchedStatus.CANCELLED;
    }

    public void markAsDone() {
        this.status = EnSchedStatus.DONE;
    }
}
