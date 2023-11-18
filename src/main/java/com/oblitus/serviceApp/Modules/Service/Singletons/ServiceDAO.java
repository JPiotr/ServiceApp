package com.oblitus.serviceApp.Modules.Service.Singletons;

import com.oblitus.serviceApp.Modules.Service.ActivityService;
import com.oblitus.serviceApp.Modules.Service.ClientService;
import com.oblitus.serviceApp.Modules.Service.CommentService;
import com.oblitus.serviceApp.Modules.Service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ServiceDAO {
    private final ClientService CLIENT_SERVICE;
    private final TicketService TICKET_SERVICE;
    private final CommentService COMMENT_SERVICE;
    private final ActivityService ACTIVITY_SERVICE;

    public ServiceDAO getInstance(){return this;}

    public ClientService getClientService(){return CLIENT_SERVICE;}

    public TicketService getTicketService(){return TICKET_SERVICE;}

    public CommentService getCommentService(){return COMMENT_SERVICE;}
    public ActivityService getActivityService(){return ACTIVITY_SERVICE;}
}
