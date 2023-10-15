package com.oblitus.serviceApp.Modules.Service.DTOs;

import com.oblitus.serviceApp.Modules.Service.Client;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class ClientResponseMapper implements Function<Client,ClientResponse> {
    @Override
    public ClientResponse apply(Client client) {
        return new ClientResponse(
                client.getID(),
                client.getName(),
                client.getCreationDate(),
                client.getLastModificationDate()
        );
    }
}
