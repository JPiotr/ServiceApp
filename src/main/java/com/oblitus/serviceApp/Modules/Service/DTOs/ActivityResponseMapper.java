package com.oblitus.serviceApp.Modules.Service.DTOs;

import com.oblitus.serviceApp.Modules.Service.Activity;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class ActivityResponseMapper implements Function<Activity, ActivityResponse> {
    @Override
    public ActivityResponse apply(Activity activity) {
        return new ActivityResponse(
                activity.getID(),
                activity.getClassName(),
                activity.getFieldName(),
                activity.getNewValue(),
                activity.getOldValue(),
                activity.getActivityType(),
                activity.getUser().getID(),
                activity.getTicket().getID(),
                activity.getCreationDate(),
                activity.getLastModificationDate()
        );
    }
}
