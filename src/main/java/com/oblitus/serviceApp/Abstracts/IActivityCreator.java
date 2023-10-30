package com.oblitus.serviceApp.Abstracts;

import com.oblitus.serviceApp.Modules.Admin.User;

public interface IActivityCreator {
    boolean createActivity(String fieldName, String newValue, String oldValue, User creator, EntityBase objectActivity);
}
