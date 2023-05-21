package com.oblitus.serviceApp.Modules.Service;

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

    public Optional<Client> getClient(UUID id){
        return repository.findById(id);
    }

    public List<Client> getAllClients(){
        return repository.findAll();
    }

    public Client addClient(String name){
        return repository.save(new Client(name));
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
        Optional<Client> opt = repository.findById(id);
        if(opt.isEmpty()){
            return null;
        }
        opt.get().setName(newName);
        return repository.save(opt.get());

    }
}
