package com.oblitus.serviceApp.Quartz.Generic;

import java.util.HashSet;
import java.util.UUID;

public abstract class WorkflowBase implements IWorkflow {
    UUID uuid;
    String name;
    UUID connectedToObject;
    HashSet<IWorkflowProcess> processCollection;
    WorkFlowDefinitionBase<WorkflowBase> definitionBase;

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
}
