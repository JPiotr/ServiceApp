package com.oblitus.serviceApp.Modules.Service.Singletons;

import com.oblitus.serviceApp.Modules.Service.DAOs.ActivityDAO;
import com.oblitus.serviceApp.Modules.Service.DAOs.ClientDAO;
import com.oblitus.serviceApp.Modules.Service.DAOs.CommentDAO;
import com.oblitus.serviceApp.Modules.Service.DAOs.TicketDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ServiceDAO {
    private final ClientDAO CLIENT_DAO;
    private final TicketDAO TICKET_DAO;
    private final CommentDAO COMMENT_DAO;
    private final ActivityDAO ACTIVITY_DAO;

    public ServiceDAO getInstance(){return this;}

    public ClientDAO getClientDao(){return CLIENT_DAO;}

    public TicketDAO getTicketDao(){return TICKET_DAO;}

    public CommentDAO getCommentDao(){return COMMENT_DAO;}
    public ActivityDAO getActivityDao(){return ACTIVITY_DAO;}
}
