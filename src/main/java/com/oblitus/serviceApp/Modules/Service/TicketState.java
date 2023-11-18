package com.oblitus.serviceApp.Modules.Service;

public enum TicketState {
    NEW,
    DONE,
    OPEN,
    @Deprecated
    PAUSED,
    REOPENED,
    @Deprecated
    CLOSE,
    ARCHIVED
}
