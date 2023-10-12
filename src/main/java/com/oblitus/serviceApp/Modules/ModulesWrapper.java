package com.oblitus.serviceApp.Modules;

import com.oblitus.serviceApp.Abstracts.ModuleBase;
import com.oblitus.serviceApp.Modules.Admin.Singletons.AdminModule;
import com.oblitus.serviceApp.Modules.Service.Singletons.ServiceModule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
@RequiredArgsConstructor
public final class ModulesWrapper {
//    public final ModulesWrapper modulesWrapper;
    public final AdminModule adminModule;
    public final ServiceModule serviceModule;


}
