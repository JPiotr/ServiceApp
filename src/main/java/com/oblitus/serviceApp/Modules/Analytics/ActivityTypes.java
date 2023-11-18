package com.oblitus.serviceApp.Modules.Analytics;

import lombok.Getter;

public enum ActivityTypes {
    USER("User"),
    TICKET("Ticket"),
    COMMENT("Comment"),
    ALL("All");
    @Getter
    private final String value;

    ActivityTypes(String value) {
        this.value = value;
    }
}
