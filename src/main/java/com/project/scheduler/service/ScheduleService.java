package com.project.scheduler.service;

import com.project.scheduler.dto.schedule.CreateScheduleDTO;
import com.project.scheduler.dto.schedule.ScheduleDTO;
import com.project.scheduler.entity.Client;
import com.project.scheduler.entity.Schedule;
import com.project.scheduler.entity.ServiceOffer;
import com.project.scheduler.mapper.ScheduleMapper;
import com.project.scheduler.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final ClientService clientService;
    private final ServiceOfferService serviceOfferService;
    private final ScheduleMapper scheduleMapper;

    public ScheduleDTO create(CreateScheduleDTO createScheduleDTO, Integer idClient, Integer idService) {

        Client client = clientService.getByIdEntity(idClient);
        ServiceOffer service = serviceOfferService.getByIdEntity(idService);

        boolean isPastDate = createScheduleDTO.getDateSched().isBefore(LocalDateTime.now());
        if(isPastDate) {
            throw new RuntimeException("Impossible to schedule a past date.");
        }

        boolean isDateUnavailable = isDateScheduled(createScheduleDTO.getDateSched(), service.getDuration());
        if(isDateUnavailable) {
            throw new RuntimeException("Date unavailable.");
        };

        boolean isServiceUnavailable = !service.getActive();
        if(isServiceUnavailable) {
            throw new RuntimeException("Service is not available.");
        }

        Schedule schedule = Schedule.create(createScheduleDTO.getDateSched(), client, service);
        Schedule createdSchedule = scheduleRepository.save(schedule);

        return scheduleMapper.toDTO(createdSchedule);
    }

    private boolean isDateScheduled(LocalDateTime date, Duration duration) {
        List<Schedule> schedules = getSchedulesByDay(LocalDate.from(date));

        for (Schedule sched : schedules) {
            LocalDateTime start = sched.getDateSched();
            LocalDateTime end = start.plus(sched.getService().getDuration());

            boolean dateIsConflictingOtherSchedules = start.isBefore(date.plus(duration)) && end.isAfter(date);
            if (dateIsConflictingOtherSchedules) {
                return true;
            }
        }
        return false;
    }

    private List<Schedule> getSchedulesByDay(LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX);

        return scheduleRepository.findAllByDateSchedBetween(startOfDay, endOfDay);
    }
}
