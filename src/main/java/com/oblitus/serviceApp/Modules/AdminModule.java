package com.oblitus.serviceApp.Modules;

import com.oblitus.serviceApp.Abstracts.ModuleBase;
import com.oblitus.serviceApp.Security.EModule;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public final class AdminModule extends ModuleBase {
    private static AdminModule module;
    public final EModule Type = EModule.ADMIN_MODULE;

    public static ModuleBase getModule(){
        if(module == null){
            module = new AdminModule();
        }
        return module;
    }
}
