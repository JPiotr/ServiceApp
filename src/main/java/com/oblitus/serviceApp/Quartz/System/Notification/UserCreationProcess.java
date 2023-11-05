package com.oblitus.serviceApp.Quartz.System.Notification;

import com.oblitus.serviceApp.Common.Email.EmailService;
import com.oblitus.serviceApp.Modules.Admin.Responses.MyProfileResponse;
import com.oblitus.serviceApp.Modules.Admin.Responses.ProfileResponse;
import com.oblitus.serviceApp.Modules.Admin.User;
import com.oblitus.serviceApp.Modules.Admin.UserService;
import com.oblitus.serviceApp.Quartz.System.Notification.SystemNotificationTypes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;


@Component
@Slf4j
public class UserCreationProcess extends EmailNotification implements Job {
    private final UserService userService;
    public UserCreationProcess(EmailService emailService, UserService userService) {
        super(emailService);
        this.userService = userService;
    }

    /**
     * @param jobExecutionContext
     * @throws JobExecutionException
     */
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.atInfo().log("User creation process notification has lunched!");
        JobDataMap dataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        User profileInfo = (User) dataMap.get(User.class.getSimpleName());
        UUID uuid = UUID.randomUUID();
        userService.saveUserSetPasswordSession(uuid,profileInfo);
//todo:prod

        emailService.sendHtmlEmail(profileInfo.getEmail(), SystemNotificationTypes.USER_CREATION.getValue(), Map.of(
                "name","Hello, " + profileInfo.getName(),
                "environmentURL","http//:localhost:3000/set-password/" + uuid,
                "environmentName","Set Your password"
                ),
                SystemNotificationTypes.USER_CREATION.getTemplate());

//        emailService.sendHtmlEmail("srvctrack@gmail.com", SystemNotificationTypes.USER_CREATION.getValue(),
//                Map.of(
//                        "name","Hello System",
//                        "environmentURL","http:localhost:3000/profile/set-password/" + uuid,
//                        "environmentName","Set Your password"
//                ),
//                SystemNotificationTypes.USER_CREATION.getTemplate());
    }
}

