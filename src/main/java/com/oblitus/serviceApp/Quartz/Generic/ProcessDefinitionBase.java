package com.oblitus.serviceApp.Quartz.Generic;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.HashSet;
import java.util.UUID;

public abstract class ProcessDefinitionBase<TWorkflowProcess extends IWorkflowProcess> implements IWorkflowProcessDefinition, Job {
    UUID uuid;

    /**
     * @param jobExecutionContext
     * @throws JobExecutionException
     */
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

    }
}

abstract class InformProcessDefinitionBase<TWorkflowProcess extends IWorkflowProcess> extends ProcessDefinitionBase<TWorkflowProcess>{

}
//process obserwujący
//process zmieniający
//process powiadamiający

abstract class ProcessBase implements IWorkflowProcess{
    IWorkflowProcess nextProcess;

}

class SubscriberProcess extends ProcessBase implements IStateChangeSubscriber{
    @Override
    public <TValue> void action(String field, TValue tValue) {

    }

    @Override
    public void subscribeFields(IStateChangePublisher publisher, HashSet<String> fieldsNames) {

    }

    @Override
    public void setNextProcess(IWorkflowProcess process) {

    }

    @Override
    public boolean checkFinished() {
        return false;
    }
}