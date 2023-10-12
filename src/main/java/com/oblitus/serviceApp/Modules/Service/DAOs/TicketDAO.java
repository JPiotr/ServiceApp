package com.oblitus.serviceApp.Modules.Service.DAOs;

import com.oblitus.serviceApp.Abstracts.DAO;
import com.oblitus.serviceApp.Modules.Service.Client;
import com.oblitus.serviceApp.Modules.Service.ClientService;
import com.oblitus.serviceApp.Modules.Service.DTOs.ClientMapper;
import com.oblitus.serviceApp.Modules.Service.DTOs.TicketDTO;
import com.oblitus.serviceApp.Modules.Service.DTOs.TicketMapper;
import com.oblitus.serviceApp.Modules.Service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountLockedException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TicketDAO implements DAO<TicketDTO> {
    private final TicketService ticketService;
    private final TicketMapper ticketMapper;
    private final ClientService clientService;
    @Override
    public Optional<TicketDTO> get(UUID id) {
        var opt = ticketService.getTicket(id);
        return opt.map(ticketMapper);
    }

    @Override
    public List<TicketDTO> getAll() {
        return ticketService.getAllTickets().stream()
                .map(ticketMapper).collect(Collectors.toList());
    }

    @Override
    public TicketDTO save(TicketDTO ticketDTO) {
        Optional<Client> client = clientService.getClient(ticketDTO.id());
        if(client.isPresent()){

            return ticketMapper.apply(
                    ticketService.addTicket(
                            ticketDTO.title(),
                            ticketDTO.description(),
                            client.get())
            );
        }
        throw new NoSuchElementException();
    }

    @Override
    public TicketDTO update(TicketDTO ticketDTO) throws AccountLockedException {
        return ticketMapper.apply(
                ticketService.updateTicket(
                        ticketDTO.id(),
                        ticketDTO.title(),
                        ticketDTO.description()
                )
        );
    }

    @Override
    public boolean delete(TicketDTO ticketDTO) {
        return ticketService.deleteTicket(ticketDTO.id());
    }
}
