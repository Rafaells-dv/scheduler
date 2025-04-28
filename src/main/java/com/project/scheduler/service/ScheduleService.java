package com.project.scheduler.service;

import com.project.scheduler.dto.filters.PageFilterDTO;
import com.project.scheduler.dto.page.PageDTO;
import com.project.scheduler.dto.schedule.CreateScheduleDTO;
import com.project.scheduler.dto.schedule.ScheduleDTO;
import com.project.scheduler.entity.Client;
import com.project.scheduler.entity.Schedule;
import com.project.scheduler.entity.ServiceOffer;
import com.project.scheduler.enums.EnOrderDirection;
import com.project.scheduler.mapper.ScheduleMapper;
import com.project.scheduler.repository.ScheduleRepository;
import com.project.scheduler.strategies.interfaces.FilterStrategy;
import com.project.scheduler.utils.FilterBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

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

    public PageDTO<ScheduleDTO> getSchedules(Integer page, Integer size, String sortBy, EnOrderDirection orderDirection, PageFilterDTO filter) {
        boolean hasNoFilter = filter == null;
        if (hasNoFilter) {
            filter = new PageFilterDTO();
            filter.setFilters(new HashMap<>());
        }

        FilterBuilder<Schedule> filterBuilder = new FilterBuilder<>(filter);

        Specification<Schedule> spec = filterBuilder.buildSpecification();

        Sort sort = orderDirection == EnOrderDirection.DESC
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Schedule> schedulesPageResult = scheduleRepository.findAll(spec, pageable);

        List<ScheduleDTO> content = schedulesPageResult.getContent().stream()
                .map(scheduleMapper::toDTO)
                .collect(Collectors.toList());

        return new PageDTO<>(
                content,
                schedulesPageResult.getNumber(),
                schedulesPageResult.getSize(),
                schedulesPageResult.getTotalElements(),
                schedulesPageResult.getTotalPages(),
                schedulesPageResult.isLast()
        );
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
