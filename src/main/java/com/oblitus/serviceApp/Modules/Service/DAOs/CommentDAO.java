package com.oblitus.serviceApp.Modules.Service.DAOs;

import com.oblitus.serviceApp.Abstracts.DAO;
import com.oblitus.serviceApp.Modules.Admin.DTOs.UserResponse;
import com.oblitus.serviceApp.Modules.Service.CommentService;
import com.oblitus.serviceApp.Modules.Service.DTOs.CommentDTO;
import com.oblitus.serviceApp.Modules.Service.DTOs.CommentResponse;
import com.oblitus.serviceApp.Modules.Service.DTOs.CommentResponseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountLockedException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CommentDAO implements DAO<CommentResponse ,CommentDTO> {
    private final CommentResponseMapper commentMapper;
    private final CommentService commentService;
    @Override
    public CommentResponse get(UUID id) {
        return commentMapper.apply(commentService.getComment(id));
    }

    @Override
    public List<CommentResponse> getAll() {
        return commentService.getAllComments().stream()
                .map(commentMapper)
                .collect(Collectors.toList());
    }

    @Override
    public CommentResponse save(CommentDTO commentDTO) {
        return commentMapper.apply(
                commentService.addComment(commentDTO));
    }

    @Override
    public CommentResponse update(CommentDTO commentDTO) throws AccountLockedException {
        return commentMapper.apply(commentService.updateComment(
                commentDTO.id(),
                commentDTO.content()));
    }

    @Override
    public boolean delete(CommentDTO commentDTO) {
        return commentService.deleteComment(commentDTO.id());
    }

    @Override
    public boolean delete(UUID id) {
        return commentService.deleteComment(id);
    }
}
