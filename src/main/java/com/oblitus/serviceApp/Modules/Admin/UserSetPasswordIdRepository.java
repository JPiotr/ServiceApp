package com.oblitus.serviceApp.Modules.Admin;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserSetPasswordIdRepository extends JpaRepository<UserSetPasswordId, UUID> {
    Optional<UserSetPasswordId> findByPasswordIDChange(UUID passwordIDChange);
    Optional<UserSetPasswordId> findByCreator(User creator);
}
