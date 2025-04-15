package com.project.scheduler.controller;

import com.project.scheduler.dto.client.CreateClientDTO;
import com.project.scheduler.dto.client.UpdateClientDTO;
import com.project.scheduler.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/client")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @PostMapping
    public ResponseEntity<?> createClient(@RequestBody CreateClientDTO dto) {
        return new ResponseEntity<>(clientService.create(dto), HttpStatus.CREATED);
    }

    @GetMapping("/{idClient}")
    public ResponseEntity<?> getClientById(@PathVariable("idClient") Integer idClient) {
        return new ResponseEntity<>(clientService.getById(idClient), HttpStatus.OK);
    }

    @PutMapping("/{idClient}")
    public ResponseEntity<?> updateClient(@PathVariable("idClient") Integer idClient, @RequestBody UpdateClientDTO dto) {
        return new ResponseEntity<>(clientService.update(idClient, dto), HttpStatus.OK);
    }

    @DeleteMapping("/{idClient}")
    public ResponseEntity<?> deleteClient(@PathVariable("idClient") Integer idClient) {
        clientService.delete(idClient);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
