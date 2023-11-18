package com.oblitus.serviceApp.Quartz.Generic;

public interface IWorkflowProcess {

    void setNextProcess(IWorkflowProcess process);

    boolean checkFinished();

}
