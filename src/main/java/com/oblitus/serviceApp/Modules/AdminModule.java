package com.oblitus.serviceApp.Modules;

import com.oblitus.serviceApp.Abstracts.ModuleBase;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public final class AdminModule extends ModuleBase {
    private static AdminModule module;

    private AdminModule(String name) {
        this.moduleName = name;
    }

    public static ModuleBase getModule(){
        if(module == null){
            module = new AdminModule("AdminModule");
        }
        return module;
    }
}
