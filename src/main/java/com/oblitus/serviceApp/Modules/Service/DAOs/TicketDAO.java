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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountLockedException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
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
    public Optional<TicketResponse> get(UUID id) {
        var opt = ticketService.getTicket(id);
        return opt.map(ticketMapper);
    }

    @Override
    public List<TicketResponse> getAll() {
        return ticketService.getAllTickets().stream()
                .map(ticketMapper).collect(Collectors.toList());
    }

    @Override
    public TicketResponse save(TicketDTO ticketDTO) {
        Optional<Client> client = clientService.getClient(ticketDTO.client());
        Optional<User> user = Optional.empty();
        if(ticketDTO.userId() != null){
            user = userService.getUser(ticketDTO.userId());
        }
        if(client.isPresent() && user.isPresent()){

            return ticketMapper.apply(
                    ticketService.addTicket(
                            new Ticket(ticketDTO.title(),
                                    ticketDTO.description(),
                                    client.get(),
                                    user.get(),
                                    ticketDTO.priority()))
            );
        }
        if(client.isPresent()){

            return ticketMapper.apply(
                    ticketService.addTicket(
                            new Ticket(ticketDTO.title(),
                                    ticketDTO.description(),
                                    client.get(),
                                    null,
                                    ticketDTO.priority()))
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
                        ticketDTO.state()
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
