package com.oblitus.serviceApp.Modules.Admin.Repos;

import com.oblitus.serviceApp.Modules.Admin.Entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {

}
