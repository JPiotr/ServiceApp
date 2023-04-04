package com.oblitus.serviceApp.Modules.Admin.Entities;

import com.oblitus.serviceApp.Abstracts.EntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;

//todo: Maybe Root Singleton Class?
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "users")
public class User extends EntityBase implements UserDetails {
    @Column(nullable = false)
    public String Email;
    @OneToMany(targetEntity = Role.class)
    public Collection<Role> Roles;

    @Column(nullable = false)
    public String Username;

    public LocalDateTime LastLoginDate;

    public LocalDateTime CredentialExpirationDate;

    public LocalDateTime AccountExpirationDate;

    private boolean Enabled;
    private boolean Expired;
    private boolean CredentialsExpired;

    //todo: what do to with password??
    private String password;


    public User(String username, String email, Collection<Role> roles, String password) {
        super();
        Username = username;
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public boolean isAccountNonExpired() {
        return Expired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !super.Locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return CredentialsExpired;
    }

    @Override
    public boolean isEnabled() {
        return Enabled;
    }

    public boolean isExpired(){
        return Expired;
    }

    public boolean isCredentialsExpired(){
        return CredentialsExpired;
    }
}
