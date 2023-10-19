package com.oblitus.serviceApp.Modules.Service.DTOs;

import com.oblitus.serviceApp.Modules.Admin.DTOs.BaseUserResponseMapper;
import com.oblitus.serviceApp.Modules.Service.Client;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class ClientResponseMapper implements Function<Client,ClientResponse> {
    private final BaseUserResponseMapper baseUserResponseMapper;
    @Override
    public ClientResponse apply(Client client) {
        return new ClientResponse(
                client.getID(),
                client.getName(),
                client.getCreationDate(),
                client.getLastModificationDate(),
                baseUserResponseMapper.apply(client.getCreator())
        );
    }
}
