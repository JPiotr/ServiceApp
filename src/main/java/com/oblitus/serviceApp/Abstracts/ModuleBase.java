package com.oblitus.serviceApp.Abstracts;

import com.oblitus.serviceApp.Modules.Admin.EModule;

public interface ModuleBase {
    //todo : implement Base Module
    static ModuleBase getModule() {
        return null;
    }

    EModule getType();
}
