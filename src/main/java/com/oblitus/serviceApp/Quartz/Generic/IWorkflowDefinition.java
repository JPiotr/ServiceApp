package com.oblitus.serviceApp.Quartz.Generic;

public interface IWorkflowDefinition extends IWFElementCreator<IWorkflow>{
    void addProcess(IWorkflowProcess process);

    void deleteProcess(IWorkflowProcess process);
}

