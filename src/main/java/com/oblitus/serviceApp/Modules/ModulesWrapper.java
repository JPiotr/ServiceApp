package com.oblitus.serviceApp.Modules;

import com.oblitus.serviceApp.Abstracts.ModuleBase;
import com.oblitus.serviceApp.Modules.Admin.Singletons.AdminModule;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public final class ModulesWrapper {
    private static ModulesWrapper modulesWrapper;
    public static final AdminModule adminModule = AdminModule.getInstance();
    private static List<ModuleBase> allModules;

    public ModulesWrapper() {
        allModules.add(adminModule);
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
