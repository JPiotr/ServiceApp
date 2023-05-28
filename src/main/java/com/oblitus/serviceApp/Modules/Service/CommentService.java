package com.oblitus.serviceApp.Modules.Service;

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

    public Optional<Comment> getComment(UUID id){
        return repository.findById(id);
    }

    public List<Comment> getAllComments(){
        return repository.findAll();
    }

    public Comment addComment(Comment comment){
        return  repository.save(comment);
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
        opt.get().setContent(content);
        return repository.save(opt.get());
    }
}
