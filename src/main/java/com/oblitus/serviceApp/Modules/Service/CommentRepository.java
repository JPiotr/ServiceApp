package com.oblitus.serviceApp.Modules.Service;

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
public interface CommentRepository extends JpaRepository<Comment, UUID> {
    Collection<Comment> findAllByTicket(Ticket ticket);
    Collection<Comment> findAllByTicket(Ticket ticket, Sort sort);
    Page<Comment> findAllByTicket(Ticket ticket, Pageable pageable);
}
