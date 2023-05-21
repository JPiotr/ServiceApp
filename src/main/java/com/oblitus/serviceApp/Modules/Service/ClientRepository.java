package com.oblitus.serviceApp.Modules.Service;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ClientRepository extends JpaRepository<Client,UUID> {
}
