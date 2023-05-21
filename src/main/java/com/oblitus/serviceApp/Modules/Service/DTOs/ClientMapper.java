package com.oblitus.serviceApp.Modules.Service.DTOs;

import com.oblitus.serviceApp.Modules.Service.Client;

import java.util.function.Function;

public class ClientMapper implements Function<Client,ClientDTO> {
    @Override
    public ClientDTO apply(Client client) {
        return new ClientDTO(
                client.getID(),
                client.getName()
        );
    }
}
