package com.oblitus.serviceApp.Modules.Admin.Singletons;

import com.oblitus.serviceApp.Abstracts.ModuleBase;
import com.oblitus.serviceApp.Modules.EModule;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public final class AdminModule implements ModuleBase{

    @Getter
    private final AdminDAO adminDAO;

    public final EModule Type = EModule.ADMIN_MODULE;

    public  ModuleBase getModule(){
        return this;
    }

    public  AdminModule getInstance(){
        return this;
    }

    @Override
    public EModule getType() {
        return Type;
    }

}
