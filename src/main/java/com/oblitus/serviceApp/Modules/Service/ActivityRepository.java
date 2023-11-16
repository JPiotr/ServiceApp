package com.oblitus.serviceApp.Modules.Service;

import com.oblitus.serviceApp.Modules.Admin.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.UUID;

@Repository
@RepositoryRestResource(exported = false)
public interface ActivityRepository extends JpaRepository<Activity, UUID> {
    Collection<Activity> findAllByObjectActivity(UUID objectActivity);
    Collection<Activity> findAllByObjectActivity(UUID objectActivity, Sort sort);
    Page<Activity> findAllByObjectActivity(UUID objectActivity, Pageable pageable);

    double countActivitiesByCreatorAndCreationDateBetween(User creator, LocalDateTime creationDate, LocalDateTime creationDate2);
    double countByCreationDateIsBetween(LocalDateTime creationDate, LocalDateTime creationDate2);
    double countByCreationDate(LocalDateTime creationDate);
    double countAllByDate(LocalDate creationDate);

    double countAllByCreatorAndClassNameAndCreationDateBetween(User creator, String className, LocalDateTime creationDate, LocalDateTime creationDate2);
    double countAllByCreatorAndClassNameAndDate(User creator, String className, LocalDate creationDate_date);
    double countAllByCreatorAndClassName(User creator, String className);
    double countAllByClassName(String className);
    double countAllByCreatorAndDate(User creator, LocalDate creationDate);
    @Query("select count(a) from Activity a")
    double countAll();
    double countActivitiesByCreator(User creator);
    double countActivitiesByObjectActivity(UUID objectActivity);

    double countAllByCreatorAndTimeBetween(User creator, LocalTime creationDate_time, LocalTime creationDate_time2);

}
