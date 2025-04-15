package com.project.scheduler.service;

import com.project.scheduler.dto.service.CreateServiceDTO;
import com.project.scheduler.dto.service.ServiceDTO;
import com.project.scheduler.dto.service.UpdateServiceDTO;
import com.project.scheduler.entity.ServiceEntity;
import com.project.scheduler.mapper.ServiceMapper;
import com.project.scheduler.repository.ServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ServiceService {
    private final ServiceRepository serviceRepository;
    private final ServiceMapper serviceMapper;

    public ServiceDTO create(CreateServiceDTO createServiceDTO) {
        ServiceEntity service = ServiceEntity.create(createServiceDTO.getTitle(), createServiceDTO.getDescription(), createServiceDTO.getPrice(), createServiceDTO.getDuration());
        ServiceEntity serviceCreated = serviceRepository.save(service);
        return serviceMapper.toDto(serviceCreated);
    }

    public ServiceDTO getById(Integer id) {
        ServiceEntity service = findById(id);
        return serviceMapper.toDto(service);
    }

    public ServiceEntity getByIdEntity(Integer id) {
        return findById(id);
    }

    public ServiceDTO update(Integer id, UpdateServiceDTO updateServiceDTO) {
        ServiceEntity service = findById(id);
        service.update(updateServiceDTO.getTitle(), updateServiceDTO.getDescription(), updateServiceDTO.getPrice(), updateServiceDTO.getDuration());
        return serviceMapper.toDto(serviceRepository.save(service));
    }

    public void delete(Integer id) {
        ServiceEntity service = findById(id);
        service.inactivate();
        serviceRepository.save(service);
    }

    private ServiceEntity findById(Integer id) {
        Optional<ServiceEntity> service = serviceRepository.findById(id);
        if (service.isEmpty()) {
            throw new RuntimeException("Service not found");
        }
        return service.get();
    }

}
