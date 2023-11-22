package com.oblitus.serviceApp.Modules.BaseModule;

import com.oblitus.serviceApp.Modules.Admin.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.UUID;

@Repository
@RepositoryRestResource(exported = false)
public interface NotificationRepository extends JpaRepository<Notification, UUID> {

    Collection<Notification> findAllByUser(User user);

}
