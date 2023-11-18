package com.oblitus.serviceApp.Modules.Admin.Exceptions;

public class AccountAlreadyExistException extends Exception {
    public AccountAlreadyExistException(String message) {
        super(message);
    }
}
