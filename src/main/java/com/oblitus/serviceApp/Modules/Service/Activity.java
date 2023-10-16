package com.oblitus.serviceApp.Modules.Service;

import com.oblitus.serviceApp.Abstracts.EntityBase;
import com.oblitus.serviceApp.Modules.Admin.User;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

//todo: notes
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
@Entity
@Table(name = "activity")
public class Activity extends EntityBase {
    private String className;
    private String fieldName;
    private String newValue;
    private String oldValue;
    private String activityType;
    @ManyToOne(targetEntity = User.class)
    private User user;
    @ManyToOne(targetEntity = Ticket.class)
    private Ticket ticket;

}

