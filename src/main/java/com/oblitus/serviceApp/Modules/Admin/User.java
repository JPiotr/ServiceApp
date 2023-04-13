package com.oblitus.serviceApp.Modules.Admin;

import com.oblitus.serviceApp.Abstracts.EntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

//todo: Maybe Root Singleton Class?
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
@Entity
@Table(name = "users")
public class User extends EntityBase implements UserDetails {
    @Column(nullable = false)
    private String Email;
    @OneToMany(targetEntity = Rule.class)
    private Collection<Rule> rules;

    @Column(nullable = false)
    private String Username;

    private LocalDateTime LastLoginDate;

    private LocalDateTime CredentialExpirationDate;

    private LocalDateTime AccountExpirationDate;

    private boolean Enabled;
    private boolean Expired;
    private boolean CredentialsExpired;

    //todo: what do to with password??
    private String password;


    public User(UUID id, String email, Collection<Rule> rules, String username, LocalDateTime lastLoginDate, LocalDateTime credentialExpirationDate, LocalDateTime accountExpirationDate, boolean enabled, boolean expired, boolean credentialsExpired, String password) {
        super();
        super.ID = id;
        Email = email;
        this.rules = rules;
        Username = username;
        LastLoginDate = lastLoginDate;
        CredentialExpirationDate = credentialExpirationDate;
        AccountExpirationDate = accountExpirationDate;
        Enabled = enabled;
        Expired = expired;
        CredentialsExpired = credentialsExpired;
        this.password = password;
    }

    protected User(String username, String email, Collection<Rule> rules, String password) {
        super();
        Username = username;
        Email = email;
        this.rules = rules;
        this.password = password;
    }

    protected User addRole(Rule rule){
        rules.add(rule);
        return this;
    }

    protected User deleteRole(Rule rule){
        rules.remove(rule);
        return this;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRules();
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
