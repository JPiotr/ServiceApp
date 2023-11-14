package com.oblitus.serviceApp.Modules.Analytics;

import com.oblitus.serviceApp.Abstracts.EntityBase;
import com.oblitus.serviceApp.Modules.Admin.User;
import com.oblitus.serviceApp.Modules.Service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnaliseActivityService {
    private final ActivityRepository activityRepository;

    public double getAllTimeRateOnCommentsByCreator(User user){
        double allDataRate = activityRepository.findAll().size();
        double rate = getRate(user,AnalyticsType.CREATOR);
        return rate == 0D ? 0D : allDataRate/rate;
    }

    public double getAllTimeRateOnObject(EntityBase base){
        double allDataRate = activityRepository.countAll();
        double rate = getRate(base,AnalyticsType.OBJECT);
        return rate == 0D ? 0D : allDataRate/rate;
    }

    private <TObject extends EntityBase> double getRate(TObject object, AnalyticsType type){
        return switch (type) {
            case CREATOR -> activityRepository.countActivitiesByCreator(object.getCreator());
            case OBJECT -> activityRepository.countActivitiesByObjectActivity(object.getUuid());
        };
    }

    public Collection<LocalDate> getHeaders(LocalDate startDate, LocalDate endDate){
        return startDate.datesUntil(endDate).toList();
    }
    public Collection<Double> getCollectionOfRatesByCreatorInPeriod(User user, LocalDate startDate, LocalDate endDate){
        List<Double> collection = new ArrayList<>();
        var dates = startDate.datesUntil(endDate);
        dates.forEach(date -> {
            collection.add(activityRepository.countAllByCreatorAndCreationDate_Date(user, date)) ;
        });
        return collection;
    }
    private void test(){
        LocalDateTime localDateTime = LocalDateTime.now();

    }
}
