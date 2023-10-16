package com.oblitus.serviceApp.Modules.Service;

import com.oblitus.serviceApp.Modules.Service.DTOs.ActivityDTO;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class TicketService {

    private final TicketRepository repository;
    private final ActivityRepository activityRepository;

    public Optional<Ticket> getTicket(UUID id){
        return repository.findById(id);
    }

    public List<Ticket> getAllTickets(){
        return repository.findAll();
    }

    public Ticket addTicket(String title, String description, Client client){
        return repository.save(new Ticket(title,description,client));
    }

    public Ticket addTicket(Ticket ticket){
        return repository.save(ticket);
    }

    public boolean deleteTicket(UUID id){
        Optional<Ticket> opt = repository.findById(id);
        if(opt.isEmpty()){
            return false;
        }
        repository.delete(opt.get());
        return true;
    }

    public Ticket updateTicket(UUID id, String title, String description, TicketPriority ticketPriority, TicketState ticketState){
        Optional<Ticket> opt = repository.findById(id);
        if(opt.isEmpty()){
            return null;
        }
        if(title != null){
            opt.get().setTitle(title);
            opt.get().setLastModificationDate();
        }
        if(description != null){
            opt.get().setDescription(description);
            opt.get().setLastModificationDate();
        }
        if(ticketPriority != null){
            var oldValue = opt.get().getPriority();
            opt.get().setPriority(ticketPriority);

            activityRepository.save(
                    new Activity(
                            EActivityHandle.TICKET.toString(),
                            "Priority",
                            ticketPriority.toString(),
                            oldValue.toString(),
                            EActivityTypes.SYSTEM.toString(),
                            null,
                            opt.get()

                    )
            );
            opt.get().setLastModificationDate();
        }
        if(ticketState != null){
            var oldValue = opt.get().getState();
            opt.get().setState(ticketState);

            activityRepository.save(
                    new Activity(
                            EActivityHandle.TICKET.toString(),
                            "State",
                            ticketState.toString(),
                            oldValue.toString(),
                            EActivityTypes.SYSTEM.toString(),
                            null,
                            opt.get()
                    )
            );
            opt.get().setLastModificationDate();
        }
        return repository.save(opt.get());

    }

}
