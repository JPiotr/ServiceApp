package com.oblitus.serviceApp.Modules.Service;

import com.oblitus.serviceApp.Modules.Admin.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;

@Repository
@RepositoryRestResource(exported = false)
public interface ActivityRepository extends JpaRepository<Activity, UUID> {
    Collection<Activity> findAllByObjectActivity(UUID objectActivity);
    Collection<Activity> findAllByObjectActivity(UUID objectActivity, Sort sort);
    Page<Activity> findAllByObjectActivity(UUID objectActivity, Pageable pageable);

    double countActivitiesByCreator(User creator);
    double countActivitiesByCreatorAndCreationDateBetween(User creator, LocalDateTime creationDate, LocalDateTime creationDate2);
    double countByCreationDateIsBetween(LocalDateTime creationDate, LocalDateTime creationDate2);
    double countByCreationDate(LocalDateTime creationDate);
    double countAll();
    double countActivitiesByObjectActivity(UUID objectActivity);

}
