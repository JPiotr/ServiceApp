package com.oblitus.serviceApp.Modules.Service.DTOs;

import com.oblitus.serviceApp.Abstracts.BaseResponseMapper;
import com.oblitus.serviceApp.Modules.Admin.Responses.ProfileResponseMapper;
import com.oblitus.serviceApp.Modules.Service.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class CommentResponseMapper extends BaseResponseMapper<CommentResponseBuilder> implements Function<Comment, CommentResponse> {
    private final ProfileResponseMapper profileResponseMapper;

    @Override
    public CommentResponse apply(Comment comment) {
        return this.useBuilder(new CommentResponseBuilder())
                .setCreator(
                        profileResponseMapper.apply(comment.getCreator())
                )
                .setContent(comment.getContent())
                .setSubject(comment.getTicket().getUuid())
                .setUUID(comment.getUuid())
                .setCreationDate(comment.getCreationDate())
                .setLastModificationDate(comment.getLastModificationDate())
                .setId(comment.getID())
                .build();
    }
}
