package com.oblitus.serviceApp.Modules.Service.Singletons;

import com.oblitus.serviceApp.Abstracts.ModuleBase;
import com.oblitus.serviceApp.Modules.Admin.Singletons.AdminDAO;
import com.oblitus.serviceApp.Modules.Admin.Singletons.AdminModule;
import com.oblitus.serviceApp.Modules.EModule;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ServiceModule implements ModuleBase {
    @Getter
    private final ServiceDAO serviceDAO;

    public final EModule Type = EModule.SERVICE_MODULE;

    //for db
    public  ModuleBase getModule(){
        return this;
    }

    public ServiceModule getInstance(){
        return this;
    }

    @Override
    public EModule getType() {
        return Type;
    }
}
