package com.oblitus.serviceApp.Modules;

import com.oblitus.serviceApp.Modules.Admin.Singletons.AdminModule;
import com.oblitus.serviceApp.Modules.BaseModule.Singletons.BaseModule;
import com.oblitus.serviceApp.Modules.Service.DTOs.ActivityResponseMapper;
import com.oblitus.serviceApp.Modules.Service.DTOs.ClientResponseMapper;
import com.oblitus.serviceApp.Modules.Service.DTOs.CommentResponseMapper;
import com.oblitus.serviceApp.Modules.Service.DTOs.TicketResponseMapper;
import com.oblitus.serviceApp.Modules.Service.Singletons.ServiceModule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public final class ModulesWrapper {
    public final AdminModule adminModule;
    public final ServiceModule serviceModule;
    public final BaseModule baseModule;


}
