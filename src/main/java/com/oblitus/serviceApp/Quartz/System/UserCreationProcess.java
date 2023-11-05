package com.oblitus.serviceApp.Quartz.System;

import com.oblitus.serviceApp.Common.Email.EmailService;
import com.oblitus.serviceApp.Modules.Admin.Responses.MyProfileResponse;
import com.oblitus.serviceApp.Modules.Admin.Responses.ProfileResponse;
import com.oblitus.serviceApp.Quartz.System.Notification.SystemNotificationTypes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

import java.util.Map;


@Component
@Slf4j
public class UserCreationProcess extends EmailNotification implements Job {
    public UserCreationProcess(EmailService emailService) {
        super(emailService);
    }

    /**
     * @param jobExecutionContext
     * @throws JobExecutionException
     */
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.atInfo().log("User creation process notification has lunched!");
        JobDataMap dataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        MyProfileResponse profileInfo = (MyProfileResponse) dataMap.get(MyProfileResponse.class.getSimpleName());

        emailService.sendHtmlEmail(profileInfo.getEmail(), SystemNotificationTypes.USER_CREATION.getValue(), Map.of(
                "name",profileInfo.getName()
        ), "helloUser.html");
        emailService.sendEmail(profileInfo.getEmail(), SystemNotificationTypes.USER_CREATION.getValue(), "Your account was created");
    }
}

@Component
@RequiredArgsConstructor
abstract class EmailNotification{
    protected final EmailService emailService;
}