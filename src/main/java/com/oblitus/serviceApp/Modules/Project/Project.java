package com.oblitus.serviceApp.Modules.Project;

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
@Table(name = "projects")
public class Project extends EntityBase {
    private String Name;
    private String Description;
    @OneToMany(targetEntity = Functionality.class)
    private Collection<Functionality> Functionalities;
    @OneToOne(targetEntity = User.class)
    private User Owner;
}
