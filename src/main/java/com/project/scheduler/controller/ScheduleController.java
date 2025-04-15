package com.project.scheduler.controller;

import com.project.scheduler.dto.schedule.CreateScheduleDTO;
import com.project.scheduler.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/schedule")
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<?> createSchedule(
            @RequestBody CreateScheduleDTO createScheduleDTO,
            @RequestParam(name = "idClient") Integer idClient,
            @RequestParam(name = "idService") Integer idService
    ) {
        return new ResponseEntity<>(scheduleService.create(createScheduleDTO, idClient, idService), HttpStatus.OK);
    }
}
