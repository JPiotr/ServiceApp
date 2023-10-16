package com.oblitus.serviceApp.Modules.Service;

public enum EActivityTypes {
    USER("User"),
    SYSTEM("System");
    private final String value;

    EActivityTypes(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
