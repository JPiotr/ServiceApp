package com.oblitus.serviceApp.Modules.Service.DTOs;

import com.oblitus.serviceApp.Modules.Admin.DTOs.BaseUserResponse;
import com.oblitus.serviceApp.Modules.Admin.DTOs.BaseUserResponseMapper;
import com.oblitus.serviceApp.Modules.Service.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class CommentResponseMapper implements Function<Comment, CommentResponse> {
    private final BaseUserResponseMapper baseUserResponseMapper;
    @Override
    public CommentResponse apply(Comment comment) {
        return new CommentResponse(
          comment.getID(),
          comment.getContent(),
          comment.getTicket().getID(),
          baseUserResponseMapper.apply(comment.getCreator()),
          comment.getCreationDate(),
          comment.getLastModificationDate()
        );
    }
}
