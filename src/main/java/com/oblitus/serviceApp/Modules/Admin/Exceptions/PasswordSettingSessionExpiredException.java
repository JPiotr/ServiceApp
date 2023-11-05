package com.oblitus.serviceApp.Modules.Admin.Exceptions;
public class PasswordSettingSessionExpiredException extends Exception {
    public PasswordSettingSessionExpiredException(String message) {
        super(message);
    }
}