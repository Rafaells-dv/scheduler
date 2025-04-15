package com.project.scheduler.mapper;

import com.project.scheduler.dto.client.ClientDTO;
import com.project.scheduler.entity.Client;
import org.mapstruct.Mapper;
import org.mapstruct.ObjectFactory;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    ClientDTO toDto(Client client);
    Client toEntity(ClientDTO clientDTO);

    @ObjectFactory
    default Client createClient(ClientDTO clientDTO) {
        return Client.create(clientDTO.getName(), clientDTO.getEmail(), clientDTO.getPhone());
    }
}
