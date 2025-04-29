package com.project.scheduler.dto.schedule;

import com.project.scheduler.dto.client.ClientDTO;
import com.project.scheduler.dto.service.ServiceDTO;
import com.project.scheduler.enums.EnSchedStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ScheduleDTO {
    private Integer id;
    private LocalDateTime dateSched;
    private ClientDTO client;
    private ServiceDTO service;
    private EnSchedStatus status;
}
