package com.oblitus.serviceApp.Quartz.Generic;

import java.util.HashSet;
import java.util.UUID;

public abstract class ProcessDefinitionBase<TWorkflowProcess extends IWorkflowProcess> implements IWorkflowProcessDefinition {


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