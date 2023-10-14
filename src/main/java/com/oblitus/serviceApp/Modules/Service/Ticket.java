package com.oblitus.serviceApp.Modules.Service;

import com.oblitus.serviceApp.Abstracts.EntityBase;
import com.oblitus.serviceApp.Modules.Admin.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
@Entity
@Table(name = "tickets")
public class Ticket extends EntityBase {
    private String Title;
    private String Description;
//    @ManyToMany(targetEntity = Comment.class)
//    private Collection<Comment> Comments;
    @ManyToOne(targetEntity = Client.class)
    private Client Client;
    @ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    private User Assigned;
    private TicketState state;
    private TicketPriority priority;

    public Ticket(String title, String description, com.oblitus.serviceApp.Modules.Service.Client client) {
        super();
        Title = title;
        Description = description;
        Client = client;
        state = TicketState.NEW;
        priority = TicketPriority.MEDIUM;
    }

    public Ticket(String title, String description, com.oblitus.serviceApp.Modules.Service.Client client, User user, TicketPriority priority){
        super();
        Title = title;
        Description = description;
        Client = client;
        state = TicketState.NEW;
        Assigned = user;
        this.priority = priority;
    }
}
