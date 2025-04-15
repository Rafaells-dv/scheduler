package com.project.scheduler.dto.schedule;

import com.project.scheduler.dto.client.ClientDTO;
import com.project.scheduler.dto.service.ServiceDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ScheduleDTO extends CreateScheduleDTO {
    private Integer id;
    private ClientDTO client;
    private ServiceDTO service;
}
