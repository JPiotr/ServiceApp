package com.oblitus.serviceApp.Modules.Admin;

import com.oblitus.serviceApp.Modules.Admin.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

}
