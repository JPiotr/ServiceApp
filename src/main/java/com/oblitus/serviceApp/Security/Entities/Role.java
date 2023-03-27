package com.oblitus.serviceApp.Security.Entities;

import com.oblitus.serviceApp.Abstracts.EntityBase;
import com.oblitus.serviceApp.Abstracts.ModuleBase;
import com.oblitus.serviceApp.Security.EModule;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Roles")
public class Role extends EntityBase {
    //todo: available modules?
    @ElementCollection
    public List<EModule> Modules;

}
