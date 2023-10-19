package com.oblitus.serviceApp.Modules.Service;

import com.oblitus.serviceApp.Abstracts.EntityBase;
import com.oblitus.serviceApp.Modules.Admin.User;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
@Entity
@Table(name = "clients")
public class Client extends EntityBase {
    private String Name;

    public Client(String name, User creator) {
        super();
        Creator = creator;
        this.ID = UUID.randomUUID();
        Name = name;
    }

    public Client(String uuid, String name, User creator) {
        super(uuid);
        Name = name;
        Creator = creator;
    }
}
