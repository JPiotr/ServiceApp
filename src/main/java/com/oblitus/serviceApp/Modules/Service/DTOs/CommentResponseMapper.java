package com.oblitus.serviceApp.Modules.Service.DTOs;

import com.oblitus.serviceApp.Modules.Service.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class CommentResponseMapper implements Function<Comment, CommentResponse> {
    @Override
    public CommentResponse apply(Comment comment) {
        return new CommentResponse(
          comment.getID(),
          comment.getContent(),
          comment.getTicket().getID(),
          comment.getCreator().getID(),
          comment.getCreationDate(),
          comment.getLastModificationDate()
        );
    }
}
