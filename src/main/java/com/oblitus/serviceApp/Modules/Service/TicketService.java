package com.oblitus.serviceApp.Modules.Service;

import com.oblitus.serviceApp.Common.File.FileService;
import com.oblitus.serviceApp.Modules.Admin.RuleService;
import com.oblitus.serviceApp.Modules.Admin.User;
import com.oblitus.serviceApp.Modules.Admin.UserService;
import jakarta.persistence.EntityNotFoundException;
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
    private final UserService userService;
    private final FileService fileService;

    public Ticket getTicket(UUID id){
        var opt = repository.findById(id);
        if(opt.isPresent()){
            return opt.get();
        }
        throw new EntityNotFoundException();
    }

    public List<Ticket> getAllTickets(){
        return repository.findAll();
    }

    public Ticket addTicket(Ticket ticket, Collection<UUID> files){
        if(files != null && !files.isEmpty()){
            for (var fileId:
                    files) {
                var file = fileService.getFileById(fileId);
                if(file != null){
                    file.setObjectId(ticket.getID());
                    fileService.updateFile(file);
                }
            }
        }

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

    public Ticket updateTicket(UUID id, String title, String description, TicketPriority ticketPriority,
                               TicketState ticketState, UUID assigned, String note, UUID edditingUser,
                               Collection<UUID> files){
        Ticket ticket = getTicket(id);
        User editor = null;
        if(edditingUser != null){
            editor = userService.getUser(edditingUser);
        }
        User usr = null;
        if(assigned != null){
            usr = userService.getUser(assigned);
            var oldValue = ticket.getAssigned();
            ticket.setAssigned(usr);
            activityRepository.save(new Activity(
                    EActivityHandle.TICKET.toString(),
                    "Assigned",
                    usr.toString(),
                    oldValue.toString(),
                    EActivityTypes.SYSTEM.toString(),
                    editor,
                    ticket)
            );
            ticket.setLastModificationDate();
        }
        if(title != null){
            ticket.setTitle(title);
            ticket.setLastModificationDate();
        }
        if(description != null){
            ticket.setDescription(description);
            ticket.setLastModificationDate();
        }
        if(ticketPriority != null){
            var oldValue = ticket.getPriority();
            ticket.setPriority(ticketPriority);

            activityRepository.save(
                    new Activity(
                            EActivityHandle.TICKET.toString(),
                            "Priority",
                            ticketPriority.toString(),
                            oldValue.toString(),
                            EActivityTypes.SYSTEM.toString(),
                            editor,
                            ticket

                    )
            );
            ticket.setLastModificationDate();
        }
        if(ticketState != null){
            var oldValue = ticket.getState();
            ticket.setState(ticketState);

            activityRepository.save(
                    new Activity(
                            EActivityHandle.TICKET.toString(),
                            "State",
                            ticketState.toString(),
                            oldValue.toString(),
                            EActivityTypes.SYSTEM.toString(),
                            editor,
                            ticket
                    )
            );
            ticket.setLastModificationDate();
        }
        if(note != null){
            var oldValue = ticket.getNote();
            activityRepository.save(
                    new Activity(
                            EActivityHandle.TICKET.toString(),
                            "Note",
                            note,
                            oldValue,
                            EActivityTypes.SYSTEM.toString(),
                            editor,
                            ticket
                    )
            );
        }
        if(!files.isEmpty()){
            for (var fileId:
                    files) {
                var file = fileService.getFileById(fileId);
                file.setObjectId(ticket.getID());
                fileService.updateFile(file);
                activityRepository.save(new Activity(
                        EActivityHandle.TICKET.toString(),
                        "Attachments",
                        file.getFileName(),
                        "",
                        EActivityTypes.SYSTEM.toString(),
                        editor,
                        ticket)
                );
                ticket.setLastModificationDate();
            }
        }

        ticket.setNote(note);
        return repository.save(ticket);

    }

}
