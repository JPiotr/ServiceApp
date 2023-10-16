package com.oblitus.serviceApp.Modules.Service;

public enum EActivityHandle {
    TICKET("Ticket"),
    COMMENT("Comment");

    private final String value;

    EActivityHandle(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
