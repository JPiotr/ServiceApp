package com.oblitus.serviceApp.Security;

import com.oblitus.serviceApp.Modules.Admin.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface TokenRepository extends JpaRepository<Token, UUID> {
    Optional<Token> findByToken(String token);
    Collection<Token> findAllValidTokenByUser(User user);

}
