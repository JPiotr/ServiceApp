package com.oblitus.serviceApp.Modules.Admin;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Repository
@RepositoryRestResource(exported = false)
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findUserByEmail(String email);
    Collection<User> findAllByUuidNot(UUID uuid);
    Collection<User> findAllByUuidNot(UUID uuid, Sort sort);
    Page<User> findAllByUuidNot(UUID uuid, Pageable pageable);
    Collection<User> findAllByRulesContains(Rule rule);
    Collection<User> findAllByRulesContainsAndUuidNot(Rule rule, UUID uuid);
    Collection<User> findAllByRulesContainsAndUuidNot(Rule rule, UUID uuid, Sort sort);
    Page<User> findAllByRulesContainsAndUuidNot(Rule rule, UUID uuid, Pageable pageable);

}
