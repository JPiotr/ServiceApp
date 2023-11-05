package com.oblitus.serviceApp.Quartz.System;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class SystemSchedulerService {
    private final Scheduler scheduler;

    public <TClass extends Job> void  scheduleJob(Class<TClass> jobClass, Map<String,Object> map){
        JobDetail jobDetail = ScheduleUtils.buildJobDetails(jobClass,map);
        Trigger trigger = ScheduleUtils.buildTrigger(jobClass);
        try{
            scheduler.scheduleJob(jobDetail,trigger);
        }
        catch (SchedulerException e){
            log.atError().log(e.getMessage());
        }
    }

    public <TClass extends Job> void  scheduleJob(Class<TClass> jobClass, Map<String,Object> map, long minDelay){

        JobDetail jobDetail = ScheduleUtils.buildJobDetails(jobClass,map);
        Trigger trigger = ScheduleUtils.buildTrigger(jobClass,Duration.ofMinutes(minDelay).toMillis());
        try{
            scheduler.scheduleJob(jobDetail,trigger);
        }
        catch (SchedulerException e){
            log.atError().log(e.getMessage());
        }
    }

    @PostConstruct
    public void init(){
        try {
            scheduler.start();
        }
        catch (Exception e){
            log.atError().log(e.getMessage());
        }
    }

    @PreDestroy
    public void preDestroy(){
        try{
            scheduler.shutdown();
        }
        catch (Exception e){
            log.atError().log(e.getMessage());
        }
    }
}
