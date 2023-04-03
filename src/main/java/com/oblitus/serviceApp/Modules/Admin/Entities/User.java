package com.oblitus.serviceApp.Modules.Admin.Entities;

import com.oblitus.serviceApp.Abstracts.EntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

//todo: Maybe Root Singleton Class?
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "users")
public class User extends EntityBase {
    @Column(nullable = false)
    public String Email;
    @OneToMany(targetEntity = Role.class)
    public Collection<Role> Roles;
    private byte[] password;

    public User(String name, String email, Collection<Role> roles, byte[] password) {
        super(name);
        Email = email;
        Roles = roles;
        this.password = password;
    }

    public User addRole(Role role){
        Roles.add(role);
        return this;
    }

    public User deleteRole(Role role){
        Roles.remove(role);
        return this;
    }
}
