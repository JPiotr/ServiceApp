package com.oblitus.serviceApp.Modules.Service.Responses;

import com.oblitus.serviceApp.Abstracts.BaseResponseMapper;
import com.oblitus.serviceApp.Modules.Admin.Responses.ProfileResponseMapper;
import com.oblitus.serviceApp.Modules.BaseModule.DTOs.FileResponseMapper;
import com.oblitus.serviceApp.Modules.BaseModule.FileService;
import com.oblitus.serviceApp.Modules.Service.Ticket;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class TicketResponseMapper extends BaseResponseMapper<TicketResponseBuilder> implements Function<Ticket, TicketResponse> {
    private final ProfileResponseMapper profileResponseMapper;
    private final FileResponseMapper fileMapper;
    private final FileService fileService;

    @Override
    public TicketResponse apply(Ticket ticket) {
        this.useBuilder(new TicketResponseBuilder())
                .setTitle(ticket.getTitle())
                .setDescription(ticket.getDescription())
                .setClient(ticket.getClient().getUuid())
                .setState(ticket.getState())
                .setPriority(ticket.getPriority())
                .setCreator(
                        profileResponseMapper.apply(ticket.getCreator())
                )
                .setNote(ticket.getNote())
                .setFiles(
                        fileService.getObjectFiles(ticket.getUuid()).stream().map(fileMapper).toList()
                )
                .setUUID(ticket.getUuid())
                .setCreationDate(ticket.getCreationDate())
                .setLastModificationDate(ticket.getLastModificationDate())
                .setId(ticket.getID());
        if (ticket.getAssigned() != null) {
            builder.setAssigned(
                    profileResponseMapper.apply(ticket.getAssigned())
            );
        }
        return builder.build();
    }
}
