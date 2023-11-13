package com.oblitus.serviceApp.Modules.Analytics;

public enum AnalyticsType {
    CREATOR("Creator"),
    OBJECT("Object")
    ;
    private final String value;
    AnalyticsType(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
