package com.oblitus.serviceApp.Modules.Admin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
interface RuleRepository extends JpaRepository<Rule, UUID> {

}
