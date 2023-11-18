package com.oblitus.serviceApp.Quartz.Generic;

import java.util.Collection;
import java.util.HashSet;
import java.util.UUID;

public abstract class WorkFlowDefinitionBase<TWorkflow extends IWorkflow> implements IWorkflowDefinition{
    public String name;
    public UUID uuid;
    protected HashSet<IWorkflowProcess> processCollection;
    protected Collection<WorkflowBase> createdWorkflows;

    @Override
    public void addProcess(IWorkflowProcess process) {
        processCollection.add(process);
    }

    @Override
    public void deleteProcess(IWorkflowProcess process) {
        processCollection.remove(process);
    }

    //todo: automatic creator from session
}

