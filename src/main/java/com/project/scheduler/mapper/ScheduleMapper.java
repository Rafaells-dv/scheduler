package com.project.scheduler.mapper;

import com.project.scheduler.dto.schedule.ScheduleDTO;
import com.project.scheduler.entity.Schedule;
import org.mapstruct.Mapper;
import org.mapstruct.ObjectFactory;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring", uses = {ClientMapper.class, ServiceMapper.class})
public interface ScheduleMapper {

    ScheduleDTO toDTO(Schedule schedule);
    Schedule toEntity(ScheduleDTO scheduleDTO);

    @ObjectFactory
    default Schedule createSchedule(ScheduleDTO scheduleDTO) {
        return Schedule.create(
                scheduleDTO.getDateSched(),
                Mappers.getMapper(ClientMapper.class).toEntity(scheduleDTO.getClient()),
                Mappers.getMapper(ServiceMapper.class).toEntity(scheduleDTO.getService())
        );
    }
}
