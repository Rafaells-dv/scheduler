package com.project.scheduler.controller;

import com.project.scheduler.dto.page.PageDTO;
import com.project.scheduler.dto.schedule.CreateScheduleDTO;
import com.project.scheduler.dto.schedule.ScheduleDTO;
import com.project.scheduler.enums.EnOrderDirection;
import com.project.scheduler.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/schedules")
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

    @GetMapping
    public ResponseEntity<PageDTO<ScheduleDTO>> getAllSchedules(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(name = "sortBy", defaultValue = "id") String sortBy,
            @RequestParam(name = "orderBy", defaultValue = "DESC") EnOrderDirection orderBy,
            @RequestParam(required = false) Map<String, Object> filters) {

        return new ResponseEntity<>(scheduleService.getSchedules(page, size, sortBy, orderBy, filters), HttpStatus.OK);
    }


}
