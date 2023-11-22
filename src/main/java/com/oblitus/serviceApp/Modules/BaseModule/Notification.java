package com.oblitus.serviceApp.Modules.BaseModule;

import com.oblitus.serviceApp.Abstracts.EntityBase;
import com.oblitus.serviceApp.Modules.Admin.User;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "notyfication-options")
public class Notification extends EntityBase {
    @ManyToOne(targetEntity = User.class)
    private User user;
    private boolean app;
    private boolean email;
    private String type;
}
