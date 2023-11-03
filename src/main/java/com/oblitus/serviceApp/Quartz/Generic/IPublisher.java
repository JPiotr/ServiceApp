package com.oblitus.serviceApp.Quartz.Generic;

public interface IPublisher {
    void addSubscriber(ISubscriber subscriber);

    void dropSubscribers();

    void dropSubscriber(ISubscriber subscriber);
}
