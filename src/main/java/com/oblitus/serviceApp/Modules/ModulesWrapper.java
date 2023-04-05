package com.oblitus.serviceApp.Modules;

import com.oblitus.serviceApp.Abstracts.ModuleBase;
import com.oblitus.serviceApp.Modules.Admin.Singletons.AdminModule;

import java.util.List;

public final class ModulesWrapper {
    private static ModulesWrapper modulesWrapper;
    private static List<ModuleBase> allModules;

    public ModulesWrapper() {
        allModules.add(AdminModule.getInstance());
    }

    public static ModulesWrapper getInstance(){
        if(modulesWrapper == null){
            modulesWrapper = new ModulesWrapper();
        }
        return modulesWrapper;
    }

    public static List<ModuleBase> getAllModules() {
        return allModules;
    }
}
