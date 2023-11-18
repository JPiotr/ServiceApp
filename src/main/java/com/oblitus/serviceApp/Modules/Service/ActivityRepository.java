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

    @Query("select count(a) from Activity a")
    double countAll();
    double countAllByCreatorAndClassNameAndDate(User creator, String className, LocalDate creationDate_date);
    double countAllByCreatorAndClassName(User creator, String className);
    double countAllByCreatorAndClassNameAndTimeBetween(User creator, String className, LocalTime time, LocalTime time2);
    double countAllByCreatorAndTimeBetween(User creator, LocalTime creationDate_time, LocalTime creationDate_time2);
    double countActivitiesByObjectActivity(UUID objectActivity);
    double countActivitiesByCreator(User creator);
    double countAllByCreatorAndDate(User creator, LocalDate creationDate);
    double countAllByFieldName(String fieldName);
    double countAllByCreatorAndClassNameAndFieldName(User creator, String className, String fieldName);
    double countAllByCreatorAndClassNameAndDateAndFieldName(User creator, String className, LocalDate date, String fieldName);
    double countAllByCreatorAndClassNameAndTimeBetweenAndFieldName(User creator, String className, LocalTime time, LocalTime time2, String fieldName);
    double countAllByCreatorAndClassNameAndDateAndFieldNameNot(User creator, String className, LocalDate date, String fieldName);






    double countActivitiesByCreatorAndCreationDateBetween(User creator, LocalDateTime creationDate, LocalDateTime creationDate2);
    double countByCreationDateIsBetween(LocalDateTime creationDate, LocalDateTime creationDate2);
    double countByCreationDate(LocalDateTime creationDate);
    double countAllByDate(LocalDate creationDate);
    double countAllByCreatorAndClassNameAndCreationDateBetween(User creator, String className, LocalDateTime creationDate, LocalDateTime creationDate2);
    double countAllByCreatorAndClassNameAndFieldNameNot(User creator, String className, String fieldName);

    double countAllByClassName(String className);

}
