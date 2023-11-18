package com.oblitus.serviceApp.Quartz.Generic;

public interface IWorkflow {
    void setDefinitionBase(WorkFlowDefinitionBase<WorkflowBase> definitionBase);
    void executeProcess();

}
