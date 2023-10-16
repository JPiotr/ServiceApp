package com.oblitus.serviceApp.Modules;

public enum EModule { //todo : do camelCase
    ADMIN_MODULE("adminModule"),
    @Deprecated
    CRM_MODULE("crmModule"),
    @Deprecated
    CASH_MODULE("cashModule"),
    @Deprecated
    FINANCE_MODULE("financeModule"),
    PROJECTS_MODULE("projectsModule"),
    SERVICE_MODULE("serviceModule"),
    BASE_MODULE("baseModule"),
    ;

    private final String value;

    EModule(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
