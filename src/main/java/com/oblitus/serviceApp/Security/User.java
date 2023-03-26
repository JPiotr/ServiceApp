package com.oblitus.serviceApp.Security;

import com.oblitus.serviceApp.Abstracts.EntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Collection;

//todo: Maybe Root Singleton Class?
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Users")
public class User extends EntityBase {
    public String Email;
    @OneToMany(targetEntity = Role.class)
    public Collection<Role> Roles;
    private String password;

    public User addRole(Role role){
        Roles.add(role);
        return this;
    }

    public User deleteRole(Role role){
        Roles.remove(role);
        return this;
    }
}
