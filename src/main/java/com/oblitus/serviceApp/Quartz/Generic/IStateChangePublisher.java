package com.oblitus.serviceApp.Quartz.Generic;

public interface IStateChangePublisher extends IPublisher {
    <TValue> void notifyFieldState(String field, TValue value);

}
