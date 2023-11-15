package com.oblitus.serviceApp.Modules.Analytics;

import com.oblitus.serviceApp.Abstracts.EntityBase;
import com.oblitus.serviceApp.Modules.Admin.User;
import com.oblitus.serviceApp.Modules.Service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AnaliseActivityService {
    private final ActivityRepository activityRepository;

    public double getAllTimeRateByCreator(User user){
        double allDataRate = activityRepository.findAll().size();
        double rate = activityRepository.countActivitiesByCreator(user);
        return rate == 0D ? 0D : allDataRate/rate;
    }

    public double getAllTimeRateOnObject(UUID objectUuid){
        double allDataRate = activityRepository.countAll();
        double rate = activityRepository.countActivitiesByObjectActivity(objectUuid);
        return rate == 0D ? 0D : allDataRate/rate;
    }

    public double getRateOnAllActivitiesByClassNameAndCreator(String className, User creator){
        double allDataRate = activityRepository.countAllByClassName(className);
        double rate = activityRepository.countAllByCreatorAndClassName(creator, className);
        return rate == 0D ? 0D : allDataRate/rate;
    }

    public Collection<Double> getCollectionOfRatesByClassNameAndCreatorInPeriod(String className, User creator, LocalDate startDate, LocalDate endDate){
        List<Double> collection = new ArrayList<>();
        var dates = startDate.datesUntil(endDate);
        dates.forEach(date -> collection.add(activityRepository.countAllByCreatorAndClassNameAndCreationDate_Date(creator,className,date)));
        return collection;
    }

    public Collection<Double> getCollectionOfRatesByClassNameAndCreator(User creator){
        double ticketRate = getRateOnAllActivitiesByClassNameAndCreator(Ticket.class.getSimpleName(),creator);
        double commentRate = getRateOnAllActivitiesByClassNameAndCreator(Comment.class.getSimpleName(), creator);
        return List.of(ticketRate,commentRate);
    }

    public Collection<Double> getCollectionOfCountsByClassNameAndCreator(User creator){
        double ticketRate = activityRepository.countAllByCreatorAndClassName(creator, Ticket.class.getSimpleName());
        double commentRate = activityRepository.countAllByCreatorAndClassName(creator, Comment.class.getSimpleName());
        return List.of(ticketRate,commentRate);
    }

    public Collection<String> getHeaders(){
        return List.of(
                Ticket.class.getSimpleName(),
                Comment.class.getSimpleName());
    }
    public Collection<LocalDate> getHeaders(LocalDate startDate, LocalDate endDate){
        return startDate.datesUntil(endDate).toList();
    }

    public Collection<LocalTime> getHeaders(int minutes){
        List<LocalTime> localTimes = new ArrayList<>();
        for (int i = 0; i < 24 * 60; i += minutes) {
            LocalTime localTime = LocalTime.of(i / 60, i % 60);
            localTimes.add(localTime);
        }
        return localTimes;
    }

    public Collection<LocalDateTime> getHeaders(LocalDate date, int minutes){
        List<LocalDateTime> collection = new ArrayList<>();
        getHeaders(minutes).stream().toList().forEach(x -> collection.add(date.atTime(x)));
        return collection;
    }
    public Collection<Double> getCollectionOfRatesByCreatorInPeriod(User user, LocalDate startDate, LocalDate endDate){
        List<Double> collection = new ArrayList<>();
        var dates = startDate.datesUntil(endDate);
        dates.forEach(date -> collection.add(activityRepository.countAllByCreatorAndCreationDate_Date(user, date)));
        return collection;
    }

    public Collection<Double> getCollectionOfCountsByCreatorInTimePeriod(User user, int minutes){
        List<Double> collection = new ArrayList<>();
        List<LocalTime> times = getHeaders(minutes).stream().toList();
        for(int i = 0; i < times.size(); i++){
            collection.add(activityRepository.countAllByCreatorAndCreationDate_TimeBetween(user,times.get(i), times.get(i+1)));
        }
        return collection;
    }
}
