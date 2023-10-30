package com.oblitus.serviceApp.Modules.Service;

import com.oblitus.serviceApp.Modules.Admin.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.UUID;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, UUID> {
    Collection<Ticket> findAllByCreatorOrAssigned(User creator, User assigned);
}
