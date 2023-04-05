package com.oblitus.serviceApp.Modules.Admin.Singletons;

import com.oblitus.serviceApp.Abstracts.ModuleBase;
import com.oblitus.serviceApp.Modules.Admin.EModule;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public final class AdminModule implements ModuleBase{
    private static AdminModule module;
//    private static final AdminDAO adminDAO = new AdminDAO();

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

    @Override
    public EModule getType() {
        return Type;
    }
}
