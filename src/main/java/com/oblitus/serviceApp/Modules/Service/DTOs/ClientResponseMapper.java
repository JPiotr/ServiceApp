package com.oblitus.serviceApp.Modules.Service.DTOs;

import com.oblitus.serviceApp.Abstracts.BaseResponseMapper;
import com.oblitus.serviceApp.Modules.Admin.Responses.ProfileResponseMapper;
import com.oblitus.serviceApp.Modules.Service.Client;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class ClientResponseMapper extends BaseResponseMapper<ClientResponseBuilder> implements Function<Client, ClientResponse> {
    private final ProfileResponseMapper profileResponseMapper;

    @Override
    public ClientResponse apply(Client client) {
        return this.useBuilder(new ClientResponseBuilder())
                .setName(client.getName())
                .setCreator(
                        profileResponseMapper.apply(client.getCreator())
                )
                .setUUID(client.getUuid())
                .setCreationDate(client.getCreationDate())
                .setLastModificationDate(client.getLastModificationDate())
                .setId(client.getID())
                .build();
    }
}
