package com.oblitus.serviceApp.Modules.Service.DTOs;

import com.oblitus.serviceApp.Modules.Service.Client;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class ClientMapper implements Function<Client,ClientDTO> {
    @Override
    public ClientDTO apply(Client client) {
        return new ClientDTO(
                client.getID(),
                client.getName()
        );
    }
}
