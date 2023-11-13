package com.oblitus.serviceApp.Modules.Analytics;

import com.oblitus.serviceApp.Abstracts.EntityBase;
import com.oblitus.serviceApp.Modules.Admin.User;
import com.oblitus.serviceApp.Modules.Service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Period;
import java.util.Collection;

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
}
