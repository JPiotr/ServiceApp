package com.oblitus.serviceApp.Modules;

import com.oblitus.serviceApp.Modules.Admin.Responses.MyProfileResponseMapper;
import com.oblitus.serviceApp.Modules.Admin.Responses.ProfileResponseMapper;
import com.oblitus.serviceApp.Modules.BaseModule.DTOs.FileResponseMapper;
import com.oblitus.serviceApp.Modules.Admin.Responses.UserResponseMapper;
import com.oblitus.serviceApp.Modules.Service.Responses.ActivityResponseMapper;
import com.oblitus.serviceApp.Modules.Service.Responses.ClientResponseMapper;
import com.oblitus.serviceApp.Modules.Service.Responses.CommentResponseMapper;
import com.oblitus.serviceApp.Modules.Service.Responses.TicketResponseMapper;
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
    public final ProfileResponseMapper profileMapper;
    public final MyProfileResponseMapper myProfileMapper;


}
