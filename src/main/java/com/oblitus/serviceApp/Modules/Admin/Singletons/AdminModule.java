package com.oblitus.serviceApp.Modules.Admin.Singletons;

import com.oblitus.serviceApp.Abstracts.ModuleBase;
import com.oblitus.serviceApp.Modules.Admin.EModule;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@Component
public final class AdminModule implements ModuleBase{
    private static AdminModule module;
    private static final AdminDAO adminDAO = new AdminDAO();

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

    public AdminDAO getAdminDAO(){
        return adminDAO;
    }
}
