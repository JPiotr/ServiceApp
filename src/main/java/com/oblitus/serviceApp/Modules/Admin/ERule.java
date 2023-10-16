package com.oblitus.serviceApp.Modules.Admin;

import java.util.Objects;

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

    public static ERule getValue(String name){
        for (var r:
             ERule.values()) {
            if(Objects.equals(r.toString(), name)) return r;
        }
        throw new IllegalArgumentException();
    }
}
