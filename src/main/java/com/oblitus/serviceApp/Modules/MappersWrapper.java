package com.oblitus.serviceApp.Modules;

import com.oblitus.serviceApp.Modules.BaseModule.DTOs.FileResponseMapper;
import com.oblitus.serviceApp.Modules.Admin.DTOs.UserResponseMapper;
import com.oblitus.serviceApp.Modules.Service.DTOs.ActivityResponseMapper;
import com.oblitus.serviceApp.Modules.Service.DTOs.ClientResponseMapper;
import com.oblitus.serviceApp.Modules.Service.DTOs.CommentResponseMapper;
import com.oblitus.serviceApp.Modules.Service.DTOs.TicketResponseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public final class MappersWrapper {
    public final ClientResponseMapper clientMapper;
    public final ActivityResponseMapper activityMapper;
    public final TicketResponseMapper ticketMapper;
    public final CommentResponseMapper commentMapper;

    public final FileResponseMapper fileMapper;

    public final UserResponseMapper userMapper;


}
