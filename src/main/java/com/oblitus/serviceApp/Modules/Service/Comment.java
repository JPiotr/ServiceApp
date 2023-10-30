package com.oblitus.serviceApp.Modules.Service;

import com.oblitus.serviceApp.Abstracts.EntityBase;
import com.oblitus.serviceApp.Modules.Admin.User;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
@Entity
@Table(name = "comments")
public class Comment extends EntityBase {
    private String content;
    @ManyToOne(targetEntity = com.oblitus.serviceApp.Modules.Service.Ticket.class)
    private Ticket ticket;

    public Comment(String content, Ticket ticket, User creator) {
        super();
        this.content = content;
        this.creator = creator;
        this.ticket = ticket;
    }
}
