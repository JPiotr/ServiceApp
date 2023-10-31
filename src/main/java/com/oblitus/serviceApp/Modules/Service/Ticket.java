package com.oblitus.serviceApp.Modules.Service;

import com.oblitus.serviceApp.Abstracts.EntityBase;
import com.oblitus.serviceApp.Modules.Admin.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;


@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
@Entity
@Table(name = "tickets")
public class Ticket extends EntityBase {
    private static long num = 0;
    private String title;
    private String description;
    @ManyToOne(targetEntity = Client.class)
    private Client client;
    @ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    private User assigned;
    private TicketState state;
    private TicketPriority priority;
    private String note;
    public Ticket(String title, String description, com.oblitus.serviceApp.Modules.Service.Client client, User assigned, TicketPriority priority, User creator, String note){
        super();
        this.title = title;
        this.description = description;
        this.client = client;
        this.state = TicketState.NEW;
        this.assigned = assigned;
        this.priority = priority;
        this.creator = creator;
        this.note = note;
        setID(Ticket.num);
        Ticket.num += 1;
    }
}
