package com.oblitus.serviceApp.Modules.Service.DTOs;

import com.oblitus.serviceApp.Modules.Admin.DTOs.UserResponseMapper;
import com.oblitus.serviceApp.Modules.Service.Ticket;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class TicketResponseMapper implements Function<Ticket, TicketResponse> {
    @Override
    public TicketResponse apply(Ticket ticket) {
        if(ticket.getAssigned() == null){
            return new TicketResponse(
                    ticket.getID(),
                    ticket.getTitle(),
                    ticket.getDescription(),
                    ticket.getClient().getID(),
                    null,
                    ticket.getState(),
                    ticket.getPriority(),
                    ticket.getCreationDate(),
                    ticket.getLastModificationDate()
            );
        }
        return new TicketResponse(
                ticket.getID(),
                ticket.getTitle(),
                ticket.getDescription(),
                ticket.getClient().getID(),
                ticket.getAssigned().getID(),
                ticket.getState(),
                ticket.getPriority(),
                ticket.getCreationDate(),
                ticket.getLastModificationDate()
        );
    }
}
