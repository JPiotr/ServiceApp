package com.oblitus.serviceApp.Security.Repos;

import com.oblitus.serviceApp.Security.Entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {

}
