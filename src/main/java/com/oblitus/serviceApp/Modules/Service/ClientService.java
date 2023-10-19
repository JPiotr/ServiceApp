package com.oblitus.serviceApp.Modules.Service;

import com.oblitus.serviceApp.Modules.Admin.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class ClientService {
    private final ClientRepository repository;
    private final UserService userService;

    public Client getClient(UUID id){
        var client = repository.findById(id);
        if(client.isPresent()){
            return client.get();
        }
        throw new EntityNotFoundException();
    }

    public List<Client> getAllClients(){
        return repository.findAll();
    }

    public Client addClient(String name, UUID creatorID){
        if(creatorID == null){
            return repository.save(new Client(name,null));
        }
        var user = userService.getUser(creatorID);
        return repository.save(new Client(name, user));
    }

    public boolean deleteClient(UUID id){
        Optional<Client> opt = repository.findById(id);
        if(opt.isEmpty()){
            return false;
        }
        repository.delete(opt.get());
        return true;
    }

    public Client updateClient(UUID id, String newName){
        Client client = getClient(id);
        client.setName(newName);
        client.setLastModificationDate();
        return repository.save(client);

    }
}
