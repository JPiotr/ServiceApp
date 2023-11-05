package com.oblitus.serviceApp.Quartz.System.Notification;

public enum SystemNotificationTypes {
    USER_CREATION("Your account has been created!");

    private final String value;
    SystemNotificationTypes(String value) {
        this.value = value;
    }

    public String getValue(){
        return value;
    }
}
