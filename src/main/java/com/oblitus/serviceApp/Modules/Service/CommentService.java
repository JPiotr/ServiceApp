package com.oblitus.serviceApp.Modules.Service;

import com.oblitus.serviceApp.Modules.Admin.UserService;
import com.oblitus.serviceApp.Modules.Service.DTOs.ActivityDTO;
import com.oblitus.serviceApp.Modules.Service.DTOs.CommentDTO;
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

    public Optional<Comment> getComment(UUID id){
        return repository.findById(id);
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
                        EActivityTypes.SYSTEM.toString(),
                        commentDTO.user(),
                        commentDTO.subject()
                )
        );
        return repository.save(
                new Comment(
                        commentDTO.content(),
                        userService.getUser(commentDTO.user()).get(),
                        ticketService.getTicket(commentDTO.subject()).get()
                ));
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
        var opt = repository.findById(id);
        if(opt.isEmpty()){
            return null;
        }
        activityService.addActivity(
                new ActivityDTO(
                        null,
                        EActivityHandle.COMMENT.toString(),
                        "Content",
                        content,
                        opt.get().getContent(),
                        EActivityTypes.SYSTEM.toString(),
                        opt.get().getCreator().getID(),
                        opt.get().getTicket().getID()
                )
        );
        opt.get().setContent(content);
        opt.get().setLastModificationDate();
        return repository.save(opt.get());
    }
}
