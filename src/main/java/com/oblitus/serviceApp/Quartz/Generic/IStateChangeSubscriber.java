package com.oblitus.serviceApp.Quartz.Generic;

import java.util.HashSet;

public interface IStateChangeSubscriber extends ISubscriber {
    @Override
    default void subscribe(IPublisher publisher) {
        publisher.addSubscriber(this);
    }

    @Override
    default void unsubscribe(IPublisher publisher) {
        publisher.dropSubscriber(this);
    }

    <TValue> void action(String field, TValue value);

    void subscribeFields(IStateChangePublisher publisher, HashSet<String> fieldsNames);
}
