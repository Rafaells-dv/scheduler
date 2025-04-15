package com.project.scheduler.repository;

import com.project.scheduler.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {
    List<Schedule> findAllByDateSchedBetween(LocalDateTime startOfDay, LocalDateTime endOfDay);
}
