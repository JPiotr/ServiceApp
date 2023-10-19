package com.oblitus.serviceApp.Modules.Service.DAOs;

import com.oblitus.serviceApp.Abstracts.DAO;
import com.oblitus.serviceApp.Modules.Admin.DTOs.UserResponse;
import com.oblitus.serviceApp.Modules.Service.ClientService;
import com.oblitus.serviceApp.Modules.Service.DTOs.ClientDTO;
import com.oblitus.serviceApp.Modules.Service.DTOs.ClientResponse;
import com.oblitus.serviceApp.Modules.Service.DTOs.ClientResponseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountLockedException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ClientDAO implements DAO<ClientResponse,ClientDTO> {
    private final ClientService service;
    private final ClientResponseMapper clientMapper;
    @Override
    public ClientResponse get(UUID id) {
        var client = service.getClient(id);
        return clientMapper.apply(client);
    }

    @Override
    public List<ClientResponse> getAll() {
        return service.getAllClients()
                .stream()
                .map(clientMapper)
                .collect(Collectors.toList());
    }

    @Override
    public ClientResponse save(ClientDTO clientDTO) {
        return clientMapper.apply(
                service.addClient(
                        clientDTO.name(),
                        clientDTO.creator()
                )
        );
    }

    @Override
    public ClientResponse update(ClientDTO clientDTO) throws AccountLockedException {
        return clientMapper.apply(
                service.updateClient(
                        clientDTO.id(),
                        clientDTO.name()
                )
        );
    }

    @Override
    public boolean delete(ClientDTO clientDTO) {
        return service.deleteClient(clientDTO.id());
    }

    @Override
    public boolean delete(UUID id) {
        return service.deleteClient(id);
    }
}
