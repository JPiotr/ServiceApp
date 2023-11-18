package com.oblitus.serviceApp.Modules.Admin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RepositoryRestResource(exported = false)
public interface RuleRepository extends JpaRepository<Rule, UUID> {
    Optional<Rule> findRuleByName(String name);
}
