package com.oblitus.serviceApp.Modules.BaseModule.Responses;

import com.oblitus.serviceApp.Abstracts.BaseResponseMapper;
import com.oblitus.serviceApp.Modules.Admin.Responses.ProfileResponseMapper;
import com.oblitus.serviceApp.Modules.BaseModule.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class NotificationResponseMapper extends BaseResponseMapper<NotificationResponseBuilder> implements Function<Notification,NotificationResponse> {
    private final ProfileResponseMapper profileResponseMapper;

    /**
     * @param notification the function argument
     * @return
     */
    @Override
    public NotificationResponse apply(Notification notification) {
        this.useBuilder(new NotificationResponseBuilder())
                .setType(notification.getType())
                .setApp(notification.isApp())
                .setEmail(notification.isEmail())
                .setUUID(notification.getUuid())
                .setLastModificationDate(notification.getLastModificationDate());

        if(notification.getUser() != null){
            builder.setUser(
                    profileResponseMapper.apply(notification.getUser())
            );
        }
        return builder.build();
    }
}
