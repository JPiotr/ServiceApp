package com.oblitus.serviceApp.Quartz.Generic;

import java.util.HashSet;
import java.util.UUID;

public class SimpleWorkflow extends WorkflowBase {
    public SimpleWorkflow(UUID connectedToObject) {
        super(connectedToObject);
    }

    public SimpleWorkflow(UUID connectedToObject, HashSet<IWorkflowProcess> processCollection) {
        super(connectedToObject, processCollection);
    }

    public SimpleWorkflow(HashSet<IWorkflowProcess> processCollection) {
        super(processCollection);
    }

    public SimpleWorkflow() {
    }
}
