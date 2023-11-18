package com.oblitus.serviceApp.Quartz.Generic;

public interface ISubscriber {
    void subscribe(IPublisher publisher);

    void unsubscribe(IPublisher publisher);
}
