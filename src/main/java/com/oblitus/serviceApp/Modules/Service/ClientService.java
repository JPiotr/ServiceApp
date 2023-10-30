package com.oblitus.serviceApp.Modules.Service;

import com.oblitus.serviceApp.Abstracts.IService;
import com.oblitus.serviceApp.Modules.Admin.DTOs.UserDTO;
import com.oblitus.serviceApp.Modules.Admin.UserService;
import com.oblitus.serviceApp.Modules.Service.DTOs.ClientDTO;
import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class ClientService implements IService<Client, ClientDTO> {
    private final ClientRepository repository;
    private final UserService userService;


    @Override
    public Client get(ClientDTO dto) {
        var client = repository.findById(dto.id());
        if(client.isPresent()){
            return client.get();
        }
        throw new EntityNotFoundException();
    }

    @Override
    public Collection<Client> getAll() {
        return repository.findAll();
    }

    @Override
    public Client update(ClientDTO dto) {
        Client client = get(dto);
        client.setName(dto.name());
        client.setLastModificationDate();
        return repository.save(client);
    }

    @Override
    public Client add(ClientDTO dto) {
        if(dto.creator() == null){
            return repository.save(new Client(dto.name(),null));
        }
        var user = userService.get(new UserDTO(dto.creator()));
        return repository.save(new Client(dto.name(), user));
    }

    @Override
    public boolean delete(ClientDTO dto) {
        Optional<Client> opt = repository.findById(dto.id());
        if(opt.isEmpty()){
            return false;
        }
        repository.delete(opt.get());
        return true;
    }
}
