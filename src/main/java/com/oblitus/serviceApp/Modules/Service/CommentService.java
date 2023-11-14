package com.oblitus.serviceApp.Modules.Service;

import com.oblitus.serviceApp.Abstracts.EntityBase;
import com.oblitus.serviceApp.Abstracts.IActivityCreator;
import com.oblitus.serviceApp.Abstracts.IService;
import com.oblitus.serviceApp.Modules.Admin.DTOs.UserDTO;
import com.oblitus.serviceApp.Modules.Admin.User;
import com.oblitus.serviceApp.Modules.Admin.UserService;
import com.oblitus.serviceApp.Modules.Service.DTOs.CommentDTO;
import com.oblitus.serviceApp.Modules.Service.DTOs.TicketDTO;
import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.UUID;

@Service
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class CommentService implements IService<Comment, CommentDTO>, IActivityCreator {
    private final CommentRepository repository;
    private final UserService userService;
    private final TicketService ticketService;
    private final ActivityFactory activityFabric;

    @Override
    public Comment get(CommentDTO dto) {
        var opt = repository.findById(dto.id());
        if(opt.isPresent()){
            return opt.get();
        }
        throw new EntityNotFoundException();
    }

    @Override
    public Collection<Comment> getAll() {
        return repository.findAll();
    }

    public Collection<Comment> getAll(Sort sort) {
        return repository.findAll(sort);
    }
    public Page<Comment> getAll(Pageable pageable){
        return repository.findAll(pageable);
    }

    @Override
    public Comment update(CommentDTO dto) {
        var comment = get(dto);
        createActivity("Content", dto.content(), comment.getContent(), null, comment);
        comment.setContent(dto.content());
        comment.setLastModificationDate();
        return repository.save(comment);
    }

    @Override
    public Comment add(CommentDTO dto) {
        var user = userService.get(new UserDTO(dto.creator()));
        var comment = repository.save(
                new Comment(
                        dto.content(),
                        ticketService.get(new TicketDTO(dto.subject()))
                        ,userService.get(new UserDTO(dto.creator()))));
        createActivity("Content", comment.getContent(), " ", user,comment);
        ticketService.createActivity("Comments", comment.getContent(), " ", user,
                ticketService.get(new TicketDTO(dto.subject())));
        return comment;
    }

    @Override
    public boolean delete(CommentDTO dto) {
        var opt = repository.findById(dto.id());
        if(opt.isEmpty()){
            return false;
        }
        repository.delete(opt.get());
        return true;
    }

    @Override
    public boolean createActivity(String fieldName, String newValue, String oldValue, User creator, EntityBase objectActivity) {
        activityFabric.prepare(Comment.class.getName())
                .create(fieldName,newValue,oldValue,creator,objectActivity);
        return true;
    }

    public Collection<Comment> getAllTicketComments(UUID ticketId, Sort sort){
        var ticket = ticketService.get(new TicketDTO(ticketId));
        return sort != null ? repository.findAllByTicket(ticket,sort) : repository.findAllByTicket(ticket);
    }

    public Page<Comment> getAllTicketComments(UUID ticketId, Pageable pageable){
        var ticket = ticketService.get(new TicketDTO(ticketId));
        return repository.findAllByTicket(ticket,pageable);
    }

}
