package com.oblitus.serviceApp.Modules.Service;

import com.oblitus.serviceApp.Abstracts.EntityBase;
import com.oblitus.serviceApp.Modules.Admin.Rule;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.*;

import java.util.Collection;
import java.util.List;

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
    private TicketState state;

    public Ticket(String title, String description, com.oblitus.serviceApp.Modules.Service.Client client) {
        Title = title;
        Description = description;
        Client = client;
        state = TicketState.NEW;
    }
}
