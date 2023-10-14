package com.oblitus.serviceApp.Modules.Service.DTOs;

import com.oblitus.serviceApp.Modules.Admin.DTOs.UserMapper;
import com.oblitus.serviceApp.Modules.Service.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class CommentMapper implements Function<Comment, CommentDTO> {
    private final UserMapper userMapper;
    @Override
    public CommentDTO apply(Comment comment) {
        return new CommentDTO(
                comment.getID(),
                comment.getContent(),
                comment.getTicket().getID(),
                comment.getCreator().getID()
        );
    }
}
