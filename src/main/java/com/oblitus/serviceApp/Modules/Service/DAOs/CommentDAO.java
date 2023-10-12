package com.oblitus.serviceApp.Modules.Service.DAOs;

import com.oblitus.serviceApp.Abstracts.DAO;
import com.oblitus.serviceApp.Modules.Service.CommentService;
import com.oblitus.serviceApp.Modules.Service.DTOs.CommentDTO;
import com.oblitus.serviceApp.Modules.Service.DTOs.CommentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountLockedException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CommentDAO implements DAO<CommentDTO> {
    private final CommentMapper commentMapper;
    private final CommentService commentService;
    @Override
    public Optional<CommentDTO> get(UUID id) {
        var opt = commentService.getComment(id);
        return opt.map(commentMapper);
    }

    @Override
    public List<CommentDTO> getAll() {
        return commentService.getAllComments().stream()
                .map(commentMapper)
                .collect(Collectors.toList());
    }

    @Override
    public CommentDTO save(CommentDTO commentDTO) {
        return commentMapper.apply(
                commentService.addComment(commentDTO.content()));
    }

    @Override
    public CommentDTO update(CommentDTO commentDTO) throws AccountLockedException {
        return commentMapper.apply(commentService.updateComment(
                commentDTO.id(),
                commentDTO.content()));
    }

    @Override
    public boolean delete(CommentDTO commentDTO) {
        return commentService.deleteComment(commentDTO.id());
    }
}
