package com.oblitus.serviceApp.Modules.Service;

import com.oblitus.serviceApp.Modules.Admin.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.UUID;

@Repository
@RepositoryRestResource(exported = false)
public interface TicketRepository extends JpaRepository<Ticket, UUID> {
    Collection<Ticket> findAllByCreatorOrAssigned(User creator, User assigned, Sort sort);
    Collection<Ticket> findAllByCreatorOrAssigned(User creator, User assigned);
    Page<Ticket> findAllByCreatorOrAssigned(User creator, User assigned, Pageable pageable);
    Collection<Ticket> findAllBySubscribersContaining(User subscriber);
    Collection<Ticket> findAllBySubscribersContaining(User subscriber, Sort sort);
    Page<Ticket> findAllBySubscribersContaining(User subscriber, Pageable pageable);



}
