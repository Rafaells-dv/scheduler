package com.project.scheduler.service;

import com.project.scheduler.dto.service.CreateServiceDTO;
import com.project.scheduler.dto.service.ServiceDTO;
import com.project.scheduler.dto.service.UpdateServiceDTO;
import com.project.scheduler.entity.ServiceOffer;
import com.project.scheduler.exception.NotFoundException;
import com.project.scheduler.mapper.ServiceMapper;
import com.project.scheduler.repository.ServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ServiceOfferService {
    private final ServiceRepository serviceRepository;
    private final ServiceMapper serviceMapper;

    public ServiceDTO create(CreateServiceDTO createServiceDTO) {
        ServiceOffer service = ServiceOffer.create(createServiceDTO.getTitle(), createServiceDTO.getDescription(), createServiceDTO.getPrice(), createServiceDTO.getDuration());
        ServiceOffer serviceCreated = serviceRepository.save(service);
        return serviceMapper.toDto(serviceCreated);
    }

    public ServiceDTO getById(Integer id) {
        return serviceMapper.toDto(findById(id));
    }

    public ServiceOffer getByIdEntity(Integer id) {
        return findById(id);
    }

    public ServiceDTO update(Integer id, UpdateServiceDTO updateServiceDTO) {
        ServiceOffer service = findById(id);
        service.update(updateServiceDTO.getTitle(), updateServiceDTO.getDescription(), updateServiceDTO.getPrice(), updateServiceDTO.getDuration());
        return serviceMapper.toDto(serviceRepository.save(service));
    }

    public void delete(Integer id) {
        ServiceOffer service = findById(id);
        service.inactivate();
        serviceRepository.save(service);
    }

    private ServiceOffer findById(Integer id) {
        Optional<ServiceOffer> service = serviceRepository.findById(id);
        if (service.isEmpty()) {
            throw new NotFoundException("Service not found");
        }
        return service.get();
    }

}
