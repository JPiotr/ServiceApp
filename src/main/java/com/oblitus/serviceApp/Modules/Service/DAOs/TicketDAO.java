package com.oblitus.serviceApp.Modules.Service.DAOs;

import com.oblitus.serviceApp.Abstracts.DAO;
import com.oblitus.serviceApp.Modules.Admin.User;
import com.oblitus.serviceApp.Modules.Admin.UserService;
import com.oblitus.serviceApp.Modules.Service.Client;
import com.oblitus.serviceApp.Modules.Service.ClientService;
import com.oblitus.serviceApp.Modules.Service.DTOs.TicketDTO;
import com.oblitus.serviceApp.Modules.Service.DTOs.TicketResponse;
import com.oblitus.serviceApp.Modules.Service.DTOs.TicketResponseMapper;
import com.oblitus.serviceApp.Modules.Service.Ticket;
import com.oblitus.serviceApp.Modules.Service.TicketService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountLockedException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TicketDAO implements DAO<TicketResponse, TicketDTO> {
    private final TicketService ticketService;
    private final TicketResponseMapper ticketMapper;
    private final ClientService clientService;
    private final UserService userService;
    @Override
    public TicketResponse get(UUID id) {
        var ticket = ticketService.getTicket(id);
        return ticketMapper.apply(ticket);
    }

    @Override
    public List<TicketResponse> getAll() {
        return ticketService.getAllTickets().stream()
                .map(ticketMapper).collect(Collectors.toList());
    }

    @Override
    public TicketResponse save(TicketDTO ticketDTO) {
        Client client = clientService.getClient(ticketDTO.client());
        User assigned = userService.getUser(ticketDTO.assigned());
        User creator = userService.getUser(ticketDTO.creator());
        if(creator == null){
            throw new EntityNotFoundException("User who is creating ticket is not found!");
        }
        if(ticketDTO.assigned() != null){
            assigned = userService.getUser(ticketDTO.assigned());
        }
            if(client != null && assigned != null){

            return ticketMapper.apply(
                    ticketService.addTicket(
                            new Ticket(ticketDTO.title(),
                                    ticketDTO.description(),
                                    client,
                                    assigned,
                                    ticketDTO.priority(),
                                    creator,
                                    ticketDTO.note()),
                                    ticketDTO.files())
            );
        }
        if(client != null){

            return ticketMapper.apply(
                    ticketService.addTicket(
                            new Ticket(ticketDTO.title(),
                                    ticketDTO.description(),
                                    client,
                                    null,
                                    ticketDTO.priority(),
                                    creator,
                                    ticketDTO.note()),
                                    ticketDTO.files())
            );
        }
        throw new NoSuchElementException();
    }

    @Override
    public TicketResponse update(TicketDTO ticketDTO) throws AccountLockedException {
        return ticketMapper.apply(
                ticketService.updateTicket(
                        ticketDTO.id(),
                        ticketDTO.title(),
                        ticketDTO.description(),
                        ticketDTO.priority(),
                        ticketDTO.state(),
                        ticketDTO.assigned(),
                        ticketDTO.note(),
                        null,
                        ticketDTO.files()
                )
        );
    }
    public TicketResponse updateWEditor(TicketDTO ticketDTO, UUID editorId) throws AccountLockedException {
        return ticketMapper.apply(
                ticketService.updateTicket(
                        ticketDTO.id(),
                        ticketDTO.title(),
                        ticketDTO.description(),
                        ticketDTO.priority(),
                        ticketDTO.state(),
                        ticketDTO.assigned(),
                        ticketDTO.note(),
                        editorId,
                        ticketDTO.files()
                )
        );
    }

    @Override
    public boolean delete(TicketDTO ticketDTO) {
        return ticketService.deleteTicket(ticketDTO.id());
    }

    @Override
    public boolean delete(UUID id) {
        return ticketService.deleteTicket(id);
    }
}
