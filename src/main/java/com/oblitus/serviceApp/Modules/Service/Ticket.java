package com.oblitus.serviceApp.Modules.Service;

import com.oblitus.serviceApp.Abstracts.EntityBase;
import com.oblitus.serviceApp.Modules.Admin.User;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
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
    @OneToMany(targetEntity = Comment.class)
    private Collection<Comment> Comments;
    @OneToOne(targetEntity = Client.class)
    private Client Client;
    @OneToOne(targetEntity = User.class)
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
