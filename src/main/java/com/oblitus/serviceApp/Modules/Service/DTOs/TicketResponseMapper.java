package com.oblitus.serviceApp.Modules.Service.DTOs;

import com.oblitus.serviceApp.Common.File.DTOs.FileResponseMapper;
import com.oblitus.serviceApp.Common.File.FileService;
import com.oblitus.serviceApp.Modules.Admin.DTOs.BaseUserResponseMapper;
import com.oblitus.serviceApp.Modules.Service.Ticket;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class TicketResponseMapper implements Function<Ticket, TicketResponse> {
    private final BaseUserResponseMapper baseUserResponseMapper;
    private final FileResponseMapper fileMapper;
    private final FileService fileService;
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
                    ticket.getLastModificationDate(),
                    baseUserResponseMapper.apply(ticket.getCreator()),
                    ticket.getNumber(),
                    ticket.getNote(),
                    fileService.getObjectFiles(ticket.getID()).stream().map(fileMapper).toList()
            );
        }
        return new TicketResponse(
                ticket.getID(),
                ticket.getTitle(),
                ticket.getDescription(),
                ticket.getClient().getID(),
                baseUserResponseMapper.apply(ticket.getAssigned()),
                ticket.getState(),
                ticket.getPriority(),
                ticket.getCreationDate(),
                ticket.getLastModificationDate(),
                baseUserResponseMapper.apply(ticket.getCreator()),
                ticket.getNumber(),
                ticket.getNote(),
                fileService.getObjectFiles(ticket.getID()).stream().map(fileMapper).toList()
        );
    }
}
