package com.oblitus.serviceApp.Modules.Admin;

public enum ERule {
    ADMIN("Admin"),
    USER("User"),
    SERVICE("Service"),
    CLIENT("Client");

    private final String value;

    ERule(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
