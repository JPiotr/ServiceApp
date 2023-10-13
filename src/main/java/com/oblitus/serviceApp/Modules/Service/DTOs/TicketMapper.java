package com.oblitus.serviceApp.Modules.Service.DTOs;

import com.oblitus.serviceApp.Modules.Admin.DTOs.UserMapper;
import com.oblitus.serviceApp.Modules.Service.Ticket;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TicketMapper implements Function<Ticket, TicketDTO> {
    private final ClientMapper clientMapper;
    private final UserMapper userMapper;
    private final CommentMapper commentMapper;
    @Override
    public TicketDTO apply(Ticket ticket) {
        return new TicketDTO(
                ticket.getID(),
                ticket.getTitle(),
                ticket.getDescription(),
                ticket.getComments().stream().map(commentMapper).collect(Collectors.toList()),
                clientMapper.apply(ticket.getClient()),
                userMapper.apply(ticket.getAssigned()),
                ticket.getState(),
                ticket.getPriority()
        );
    }
}
