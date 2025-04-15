package com.project.scheduler.service;

import com.project.scheduler.dto.client.ClientDTO;
import com.project.scheduler.dto.client.CreateClientDTO;
import com.project.scheduler.dto.client.UpdateClientDTO;
import com.project.scheduler.entity.Client;
import com.project.scheduler.mapper.ClientMapper;
import com.project.scheduler.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    public ClientDTO create(CreateClientDTO createClientDTO) {
        Client createCliente = Client.create(createClientDTO.getName(), createClientDTO.getEmail(), createClientDTO.getPhone());
        Client createdClient = clientRepository.save(createCliente);
        return clientMapper.toDto(createdClient);
    }

    public ClientDTO getById(Integer id) {
        return clientMapper.toDto(findById(id));
    }

    public Client getByIdEntity(Integer id) {
        return findById(id);
    }

    public ClientDTO update(Integer id, UpdateClientDTO updateClientDTO) {
        Client updateClient = findById(id);
        updateClient.update(updateClientDTO.getName(), updateClientDTO.getEmail(), updateClientDTO.getPhone());
        Client updatedClient = clientRepository.save(updateClient);
        return clientMapper.toDto(updatedClient);
    }

    public void delete(Integer id) {
        Client client = findById(id);
        client.inactivate();
        clientRepository.save(client);
    }

    private Client findById(Integer id) {
        Optional<Client> client = clientRepository.findById(id);
        if(client.isEmpty()) {
            throw new RuntimeException("Client not found");
        }

        return client.get();
    }
}
