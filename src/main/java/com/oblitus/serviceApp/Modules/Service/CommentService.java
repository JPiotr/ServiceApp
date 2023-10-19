package com.oblitus.serviceApp.Modules.Service;

import com.oblitus.serviceApp.Modules.Admin.UserService;
import com.oblitus.serviceApp.Modules.Service.DTOs.ActivityDTO;
import com.oblitus.serviceApp.Modules.Service.DTOs.CommentDTO;
import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class CommentService {
    private final CommentRepository repository;
    private final UserService userService;
    private final TicketService ticketService;
    private final ActivityService activityService;

    public Comment getComment(UUID id){
        var opt = repository.findById(id);
        if(opt.isPresent()){
            return opt.get();
        }
        throw new EntityNotFoundException();
    }

    public List<Comment> getAllComments(){
        return repository.findAll();
    }

    public Comment addComment(CommentDTO commentDTO){
        activityService.addActivity(
                new ActivityDTO(
                        null,
                        EActivityHandle.COMMENT.toString(),
                        "Content",
                        commentDTO.content(),
                        "",
                        EActivityTypes.USER.toString(),
                        commentDTO.creator(),
                        commentDTO.subject()
                )
        );
        return repository.save(
                new Comment(
                        commentDTO.content(),
                        ticketService.getTicket(commentDTO.subject())
                        ,userService.getUser(commentDTO.creator())));
    }

    public boolean deleteComment(UUID id){
        var opt = repository.findById(id);
        if(opt.isEmpty()){
            return false;
        }
        repository.delete(opt.get());
        return true;

    }

    public Comment updateComment(UUID id, String content){
        var comment = getComment(id);
        activityService.addActivity(
                new ActivityDTO(
                        null,
                        EActivityHandle.COMMENT.toString(),
                        "Content",
                        content,
                        comment.getContent(),
                        EActivityTypes.USER.toString(),
                        comment.getCreator().getID(),
                        comment.getTicket().getID()
                )
        );
        comment.setContent(content);
        comment.setLastModificationDate();
        return repository.save(comment);
    }
}
