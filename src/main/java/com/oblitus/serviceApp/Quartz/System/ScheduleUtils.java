package com.oblitus.serviceApp.Quartz.System;

import com.oblitus.serviceApp.Quartz.System.Notification.UserCreationProcess;
import org.quartz.*;

import java.util.Date;
import java.util.Map;

public class ScheduleUtils {
     public static <TClass extends Job> JobDetail buildJobDetails(Class<TClass> jobClass, Map<String, Object> map){
        final JobDataMap jobDataMap = new JobDataMap(map);
        return JobBuilder.newJob(jobClass)
                .withIdentity(jobClass.getSimpleName()).setJobData(jobDataMap).build();
    }

    public static <TClass extends Job> Trigger buildTrigger(Class<TClass> jobClass){
        SimpleScheduleBuilder builder = SimpleScheduleBuilder.simpleSchedule();
        return TriggerBuilder.newTrigger().withIdentity(jobClass.getSimpleName())
                .withSchedule(builder)
                .startNow().build();
    }

    public static <TClass extends Job> Trigger buildTrigger(Class<TClass> jobClass, long delay){
        SimpleScheduleBuilder builder = SimpleScheduleBuilder.simpleSchedule();
        return TriggerBuilder.newTrigger().withIdentity(jobClass.getSimpleName())
                .withSchedule(builder)
                .startAt(new Date(System.currentTimeMillis()+delay)).build();
    }
}
