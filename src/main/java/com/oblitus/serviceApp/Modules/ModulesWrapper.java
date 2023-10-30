package com.oblitus.serviceApp.Modules;

import com.oblitus.serviceApp.Modules.Admin.Singletons.AdminModule;
import com.oblitus.serviceApp.Modules.Service.Singletons.ServiceModule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public final class ModulesWrapper {
    public final AdminModule adminModule;
    public final ServiceModule serviceModule;


}
