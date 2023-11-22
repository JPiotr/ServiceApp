package com.oblitus.serviceApp.Modules.BaseModule.Singletons;

import com.oblitus.serviceApp.Abstracts.ModuleBase;
import com.oblitus.serviceApp.Modules.EModule;
import com.oblitus.serviceApp.Modules.Service.Singletons.ServiceModule;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BaseModule implements ModuleBase {
    @Getter
    private final BaseDAO baseDAO;

    public final EModule Type = EModule.BASE_MODULE;

    //for db
    public  ModuleBase getModule(){
        return this;
    }

    public BaseModule getInstance(){
        return this;
    }

    @Override
    public EModule getType() {
        return Type;
    }
}
