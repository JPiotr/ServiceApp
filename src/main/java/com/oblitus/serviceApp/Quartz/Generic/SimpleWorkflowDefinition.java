package com.oblitus.serviceApp.Quartz.Generic;

import java.util.HashSet;
import java.util.UUID;

public class SimpleWorkflowDefinition extends WorkFlowDefinitionBase<SimpleWorkflow> {
    @Override
    public SimpleWorkflow createFromDefinition() {
        return new SimpleWorkflow();
    }

    @Override
    public SimpleWorkflow createFromDefinition(UUID connectedObjectId) {
        return new SimpleWorkflow(connectedObjectId);
    }

    @Override
    public SimpleWorkflow createFromDefinition(UUID connectedObjectId, HashSet<IWorkflowProcess> processes) {
        return new SimpleWorkflow(connectedObjectId, processes);
    }

    @Override
    public SimpleWorkflow createFromDefinition(HashSet<IWorkflowProcess> processes) {
        return new SimpleWorkflow(processes);
    }
}
