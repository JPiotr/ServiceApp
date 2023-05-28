package com.oblitus.serviceApp.Modules.Service;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class TicketService {

    private final TicketRepository repository;

    public Optional<Ticket> getTicket(UUID id){
        return repository.findById(id);
    }

    public List<Ticket> getAllTickets(){
        return repository.findAll();
    }

    public Ticket addTicket(String title, String description, Client client){
        return repository.save(new Ticket(title,description,client));
    }

    public boolean deleteTicket(UUID id){
        Optional<Ticket> opt = repository.findById(id);
        if(opt.isEmpty()){
            return false;
        }
        repository.delete(opt.get());
        return true;
    }

    public Ticket updateClient(UUID id, String title, String description){
        Optional<Ticket> opt = repository.findById(id);
        if(opt.isEmpty()){
            return null;
        }
        if(title != null){
            opt.get().setTitle(title);
        }
        if(description != null){
            opt.get().setDescription(description);
        }
        return repository.save(opt.get());

    }

}
