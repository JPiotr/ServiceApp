package com.oblitus.serviceApp.Modules.Admin;

import com.oblitus.serviceApp.Modules.Admin.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

}
