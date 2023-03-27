package com.oblitus.serviceApp.Security.Repos;

import com.oblitus.serviceApp.Security.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

}
