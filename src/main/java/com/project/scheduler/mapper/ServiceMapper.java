package com.project.scheduler.mapper;

import com.project.scheduler.dto.service.ServiceDTO;
import com.project.scheduler.entity.ServiceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ObjectFactory;

@Mapper(componentModel = "spring")
public interface ServiceMapper {
    ServiceDTO toDto(ServiceEntity serviceEntity);
    ServiceEntity toEntity(ServiceDTO serviceDTO);

    @ObjectFactory
    default ServiceEntity createService(ServiceDTO serviceDTO){
        return ServiceEntity.create(serviceDTO.getTitle(), serviceDTO.getDescription(), serviceDTO.getPrice(), serviceDTO.getDuration());
    }
}
