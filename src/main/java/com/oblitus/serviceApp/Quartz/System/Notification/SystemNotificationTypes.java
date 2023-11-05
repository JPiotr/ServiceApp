package com.oblitus.serviceApp.Quartz.System.Notification;

import lombok.Getter;

@Getter
public enum SystemNotificationTypes {
    USER_CREATION("Your account has been created!","new-account.html");

    private final String value;
    private final String template;
    SystemNotificationTypes(String value, String template) {
        this.template = template;
        this.value = value;
    }

}
