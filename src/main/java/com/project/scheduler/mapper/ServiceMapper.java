package com.project.scheduler.mapper;

import com.project.scheduler.dto.service.ServiceDTO;
import com.project.scheduler.entity.ServiceOffer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ObjectFactory;

@Mapper(componentModel = "spring")
public interface ServiceMapper {
    ServiceDTO toDto(ServiceOffer serviceEntity);

    ServiceOffer toEntity(ServiceDTO serviceDTO);

    @ObjectFactory
    default ServiceOffer createService(ServiceDTO serviceDTO){
        return ServiceOffer.create(serviceDTO.getTitle(), serviceDTO.getDescription(), serviceDTO.getPrice(), serviceDTO.getDuration());
    }
}
