package com.oblitus.serviceApp.Modules.Service;

import com.oblitus.serviceApp.Abstracts.EntityBase;
import com.oblitus.serviceApp.Abstracts.IActivityCreator;
import com.oblitus.serviceApp.Abstracts.IService;
import com.oblitus.serviceApp.Modules.BaseModule.FileService;
import com.oblitus.serviceApp.Modules.Admin.DTOs.UserDTO;
import com.oblitus.serviceApp.Modules.Admin.User;
import com.oblitus.serviceApp.Modules.Admin.UserService;
import com.oblitus.serviceApp.Modules.Service.DTOs.ClientDTO;
import com.oblitus.serviceApp.Modules.Service.DTOs.TicketDTO;
import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class TicketService implements IService<Ticket, TicketDTO>, IActivityCreator {

    private final TicketRepository repository;
    private final ActivityFactory activityFabric;
    private final UserService userService;
    private final ClientService clientService;
    private final FileService fileService;

    @Override
    public boolean createActivity(String fieldName, String newValue, String oldValue, User creator, EntityBase objectActivity) {
        activityFabric.prepare(Ticket.class.getName())
                .create(fieldName,newValue,oldValue,creator,objectActivity);
        return true;
    }

    @Override
    public Ticket get(TicketDTO dto) {
        var opt = repository.findById(dto.id());
        if(opt.isPresent()){
            return opt.get();
        }
        throw new EntityNotFoundException();
    }

    @Override
    public Collection<Ticket> getAll() {
        return repository.findAll();
    }

    public Page<Ticket> getAll(Pageable pageable){
        return repository.findAll(pageable);
    }

    public Collection<Ticket> getAll(Sort sort) {
        return repository.findAll(sort);
    }

    @Override
    public Ticket update(TicketDTO dto) {
        Ticket ticket = get(dto);
        User editor = null;
        if(dto.editing() != null){
            editor = userService.get(new UserDTO(dto.editing()));
        }
        User usr = null;
        if(dto.assigned() != null){
            usr = userService.get(new UserDTO(dto.assigned()));
            createActivity("Assigned"
                    , usr.getName() +" "+usr.getSurname()
                    ,ticket.getAssigned().getName() +" "+ticket.getAssigned().getSurname(),editor,ticket);
            ticket.setAssigned(usr);
            ticket.setLastModificationDate();
        }
        if(dto.title() != null){
            var oldValue = ticket.getTitle();
            ticket.setTitle(dto.title());
            createActivity("Title", dto.title(), oldValue, editor, ticket);
            ticket.setLastModificationDate();
        }
        if(dto.description() != null){
            var oldValue = ticket.getDescription();
            ticket.setDescription(dto.description());
            createActivity("Description", dto.description(), oldValue, editor, ticket);
            ticket.setLastModificationDate();
        }
        if(dto.priority() != null){
            var oldValue = ticket.getPriority();
            ticket.setPriority(dto.priority());
            createActivity("Priority",dto.priority().toString(),oldValue.toString(),editor,ticket);
            ticket.setLastModificationDate();
        }
        if(dto.state() != null){
            var oldValue = ticket.getState();
            ticket.setState(dto.state());
            createActivity("State",dto.state().toString(),oldValue.toString(),editor,ticket);
            ticket.setLastModificationDate();
        }
        if(dto.note() != null){
            var oldValue = ticket.getNote();
            createActivity("Note",dto.note(),oldValue,editor,ticket);
            ticket.setNote(dto.note());
            ticket.setLastModificationDate();
        }
        if(dto.files() != null && !dto.files().isEmpty()){
            for (var fileId:
                    dto.files()) {
                var file = fileService.getFileById(fileId);
                if(file != null){
                    file.setObjectId(ticket.getUuid());
                    fileService.updateFile(file);
                    createActivity("Attachments",file.getFileName(),"",editor,ticket);
                    ticket.setLastModificationDate();
                }

            }
        }

        return repository.save(ticket);
    }

    @Override
    public Ticket add(TicketDTO dto) {
        var ticket = new Ticket(
                dto.title(),
                dto.description(),
                clientService.get(new ClientDTO(dto.client())),
                userService.get(new UserDTO(dto.assigned())),
                dto.priority(),
                userService.get(new UserDTO(dto.creator())),
                dto.note());
        if(dto.files() != null && !dto.files().isEmpty()){
            for (var fileId:
                    dto.files()) {
                var file = fileService.getFileById(fileId);
                if(file != null){
                    file.setObjectId(ticket.getUuid());
                    fileService.updateFile(file);
                    createActivity("Attachments",file.getFileName(),"",ticket.getCreator(),ticket);
                    ticket.setLastModificationDate();
                }

            }
        }
        createActivity("New Ticket","","",ticket.getCreator(),ticket);
        return repository.save(ticket);
    }

    @Override
    public boolean delete(TicketDTO dto) {
        Optional<Ticket> opt = repository.findById(dto.id());
        if(opt.isEmpty()){
            return false;
        }
        repository.delete(opt.get());
        return true;
    }

    public Collection<Ticket> getAllUserTickets(UUID userId, Sort sort){
        var user = userService.get(new UserDTO(userId));
        return sort != null ?  repository.findAllByCreatorOrAssigned(user,user,sort) :
                repository.findAllByCreatorOrAssigned(user,user);
    }
    public Page<Ticket> getAllUserTickets(UUID userID, Pageable pageable){
        var user = userService.get(new UserDTO(userID));
        return repository.findAllByCreatorOrAssigned(user,user, pageable);
    }

    public Collection<Ticket> getAllSubscribedTickets(UUID userID, Sort sort){
        var user = userService.get(new UserDTO(userID));
        return sort != null ? repository.findAllBySubscribersContaining(user, sort) :
                repository.findAllBySubscribersContaining(user);
    }

    public Page<Ticket> getAllSubscribedTickets(UUID userId, Pageable pageable){
        var user = userService.get(new UserDTO(userId));
        return repository.findAllBySubscribersContaining(user,pageable);
    }

    public Ticket subscribeTicket(UUID userUuid, UUID ticketUuid){
        var ticket = get(new TicketDTO(ticketUuid));
        var user = userService.get(new UserDTO(userUuid));
        ticket.getSubscribers().add(user);
        ticket.setLastModificationDate();
        return repository.save(ticket);
    }

    public Ticket unsubscribeTicket(UUID userUuid, UUID ticketUuid){
        var ticket = get(new TicketDTO(ticketUuid));
        var user = userService.get(new UserDTO(userUuid));
        ticket.getSubscribers().remove(user);
        ticket.setLastModificationDate();
        return repository.save(ticket);

    }

}
