package com.oblitus.serviceApp.Quartz.Generic;

import lombok.Data;
import lombok.Getter;

import java.util.HashSet;
import java.util.UUID;

@Data
public abstract class WorkflowBase implements IWorkflow {
    public UUID uuid;
    public String name;
    public UUID connectedToObject;
    private HashSet<IWorkflowProcess> processCollection;
    @Getter
    private WorkFlowDefinitionBase<WorkflowBase> definitionBase;

    public WorkflowBase(UUID connectedToObject) {
        this.connectedToObject = connectedToObject;
    }

    public WorkflowBase(UUID connectedToObject, HashSet<IWorkflowProcess> processCollection) {
        this.connectedToObject = connectedToObject;
        this.processCollection = processCollection;
    }

    public WorkflowBase(HashSet<IWorkflowProcess> processCollection) {
        this.processCollection = processCollection;
    }

    public WorkflowBase() {
    }

    @Override
    public void setDefinitionBase(WorkFlowDefinitionBase<WorkflowBase> definitionBase) {
        this.definitionBase = definitionBase;
    }

    @Override
    public void executeProcess() {

    }
}
