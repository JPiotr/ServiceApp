package com.oblitus.serviceApp.Modules.Service;

import com.oblitus.serviceApp.Abstracts.EntityBase;
import com.oblitus.serviceApp.Modules.Admin.User;
import jakarta.persistence.*;
import lombok.*;


@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
@Entity
@Table(name = "tickets")
public class Ticket extends EntityBase {
    private String Title;
    private String Description;
    @ManyToOne(targetEntity = Client.class)
    private Client Client;
    @ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    private User Assigned;
    private TicketState State;
    private TicketPriority Priority;
    private String Note;

    public Ticket(String title, String description, com.oblitus.serviceApp.Modules.Service.Client client, User assigned, TicketPriority priority, User creator, String note){
        super();
        Title = title;
        Description = description;
        Client = client;
        State = TicketState.NEW;
        Assigned = assigned;
        Priority = priority;
        Creator = creator;
        Note = note;
    }
}
