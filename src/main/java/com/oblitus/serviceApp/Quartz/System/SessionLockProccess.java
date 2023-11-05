package com.oblitus.serviceApp.Quartz.System;

import com.oblitus.serviceApp.Modules.Admin.User;
import com.oblitus.serviceApp.Modules.Admin.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Component;
@Component
@Slf4j
@RequiredArgsConstructor
public class SessionLockProccess implements Job {
    private final UserService userService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        log.atInfo().log("Session lock process started!");
        JobDataMap dataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        User profileInfo = (User) dataMap.get(User.class.getSimpleName());
        userService.deleteUserSetPasswordId(profileInfo);
    }
}