package com.project.scheduler.controller;

import com.project.scheduler.dto.service.CreateServiceDTO;
import com.project.scheduler.dto.service.UpdateServiceDTO;
import com.project.scheduler.service.ServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/service")
@RequiredArgsConstructor
public class ServiceController {

    private final ServiceService serviceService;

    @PostMapping
    public ResponseEntity<?> createService(@RequestBody CreateServiceDTO dto) {
        return new ResponseEntity<>(serviceService.create(dto), HttpStatus.CREATED);
    }

    @GetMapping("/{idService}")
    public ResponseEntity<?> getServiceById(@PathVariable("idService") Integer idService) {
        return new ResponseEntity<>(serviceService.getById(idService), HttpStatus.OK);
    }

    @PutMapping("/{idService}")
    public ResponseEntity<?> updateService(@PathVariable("idService") Integer idService, @RequestBody UpdateServiceDTO dto) {
        return new ResponseEntity<>(serviceService.update(idService, dto), HttpStatus.OK);
    }

    @DeleteMapping("/{idService}")
    public ResponseEntity<?> deleteService(@PathVariable("idService") Integer idService) {
        serviceService.delete(idService);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
