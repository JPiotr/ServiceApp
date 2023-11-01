package com.oblitus.serviceApp.Modules.Service.DTOs;

import com.oblitus.serviceApp.Abstracts.BaseResponseMapper;
import com.oblitus.serviceApp.Modules.Admin.DTOs.ProfileResponseMapper;
import com.oblitus.serviceApp.Modules.Service.Activity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class ActivityResponseMapper extends BaseResponseMapper<ActivityResponseBuilder> implements Function<Activity, ActivityResponse> {
    private final ProfileResponseMapper profileResponseMapper;
    @Override
    public ActivityResponse apply(Activity activity) {
        this.useBuilder(new ActivityResponseBuilder())
                .setClassName(activity.getClassName())
                .setFieldName(activity.getFieldName())
                .setNewValue(activity.getNewValue())
                .setOldValue(activity.getOldValue())
                .setActivityType(activity.getActivityType())
                .setObjectUuid(activity.getObjectActivity())
                .setUUID(activity.getUuid())
                .setCreationDate(activity.getCreationDate())
                .setLastModificationDate(activity.getLastModificationDate());

        if(activity.getCreator() != null){
            builder.setCreator(
                    profileResponseMapper.apply(activity.getCreator())
            );
        }
        return builder.build();
    }
}
