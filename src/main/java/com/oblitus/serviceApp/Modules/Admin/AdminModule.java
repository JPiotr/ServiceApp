package com.oblitus.serviceApp.Modules.Admin;

import com.oblitus.serviceApp.Abstracts.ModuleBase;
import lombok.NoArgsConstructor;

@NoArgsConstructor
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
