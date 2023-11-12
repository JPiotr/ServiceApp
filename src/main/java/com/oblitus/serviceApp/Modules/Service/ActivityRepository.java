package com.oblitus.serviceApp.Modules.Service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.UUID;

@Repository
@RepositoryRestResource(exported = false)
public interface ActivityRepository extends JpaRepository<Activity, UUID> {
    Collection<Activity> findAllByObjectActivity(UUID objectActivity);
}
