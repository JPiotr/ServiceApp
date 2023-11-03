package com.oblitus.serviceApp.Quartz.Generic;

import java.util.HashSet;
import java.util.UUID;

public interface IWFElementCreator<T> {
    T createFromDefinition();

    T createFromDefinition(UUID connectedObjectId);

    T createFromDefinition(UUID connectedObjectId, HashSet<IWorkflowProcess> processes);

    T createFromDefinition(HashSet<IWorkflowProcess> processes);

}
