package com.oblitus.serviceApp.Modules.Service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.UUID;

@Repository
public interface CommentRepository extends JpaRepository<Comment, UUID> {
    Collection<Comment> findAllByTicket(Ticket ticket);
}
