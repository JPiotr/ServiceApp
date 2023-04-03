package com.oblitus.serviceApp.Modules;

import com.oblitus.serviceApp.Abstracts.ModuleBase;
import com.oblitus.serviceApp.Security.EModule;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public final class AdminModule extends ModuleBase {
    private static AdminModule module;

    public final EModule Type = EModule.ADMIN_MODULE;

    //for db
    public static ModuleBase getModule(){
        if(module == null){
            module = new AdminModule();
        }
        return module;
    }

    public static AdminModule getInstance(){
        if(module == null){
            module = new AdminModule();
        }
        return module;
    }
}
