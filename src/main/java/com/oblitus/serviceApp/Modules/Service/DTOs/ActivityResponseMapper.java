package com.oblitus.serviceApp.Modules.Service.DTOs;

import com.oblitus.serviceApp.Modules.Admin.DTOs.BaseUserResponseMapper;
import com.oblitus.serviceApp.Modules.Service.Activity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class ActivityResponseMapper implements Function<Activity, ActivityResponse> {
    private final BaseUserResponseMapper baseUserResponseMapper;
    @Override
    public ActivityResponse apply(Activity activity) {
        if(activity.getUser() == null){
            return new ActivityResponse(
                    activity.getID(),
                    activity.getClassName(),
                    activity.getFieldName(),
                    activity.getNewValue(),
                    activity.getOldValue(),
                    activity.getActivityType(),
                    null,
                    activity.getTicket().getID(),
                    activity.getCreationDate(),
                    activity.getLastModificationDate()
            );
        }
        return new ActivityResponse(
                activity.getID(),
                activity.getClassName(),
                activity.getFieldName(),
                activity.getNewValue(),
                activity.getOldValue(),
                activity.getActivityType(),
                baseUserResponseMapper.apply(activity.getUser()),
                activity.getTicket().getID(),
                activity.getCreationDate(),
                activity.getLastModificationDate()
        );
    }
}
