package com.oblitus.serviceApp.Quartz.System;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Scheduler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SystemSchedulerService {
    private final Scheduler scheduler;

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
