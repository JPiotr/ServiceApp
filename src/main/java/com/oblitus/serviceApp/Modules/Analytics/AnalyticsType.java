package com.oblitus.serviceApp.Modules.Analytics;

import lombok.Getter;

@Getter
public enum AnalyticsType {
    CREATOR("Creator"),
    OBJECT("Object")
    ;
    private final String value;
    AnalyticsType(String value){
        this.value = value;
    }

}
