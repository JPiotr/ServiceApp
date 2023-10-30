package com.oblitus.serviceApp.Modules.Service;

import com.oblitus.serviceApp.Abstracts.EntityBase;
import com.oblitus.serviceApp.Modules.Admin.User;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
@Entity
@Table(name = "clients")
public class Client extends EntityBase {
    private String name;

    public Client(String name, User creator) {
        super();
        this.creator = creator;
        this.uuid = UUID.randomUUID();
        this.name = name;
    }

    public Client(String uuid, String name, User creator) {
        super(uuid);
        this.name = name;
        this.creator = creator;
    }
}
