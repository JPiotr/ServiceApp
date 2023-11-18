package com.oblitus.serviceApp.Quartz.Generic;

public interface IGWorkflowDefinition<IWorkflow> extends IWFElementCreator<IWorkflow> {
    void addProcess(IWorkflowProcess process);

    void deleteProcess(IWorkflowProcess process);
}
