package com.oblitus.serviceApp.Modules.Admin;

import com.oblitus.serviceApp.Abstracts.EntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
@Entity
@Table(name = "users")
public class User extends EntityBase implements UserDetails, CredentialsContainer {
    @Column(nullable = false)
    private String Email;
    @OneToMany(targetEntity = Rule.class)
    private Collection<Rule> Rules;

    @Column(nullable = false)
    private String Username;

    private String Name;
    private String Surname;

    private LocalDateTime LastLoginDate;

    private LocalDateTime CredentialExpirationDate;

    private LocalDateTime AccountExpirationDate;

//    @Override
    @Getter
    private boolean Enabled;
    @Getter
    private boolean Expired;
    @Getter
    private boolean CredentialsExpired;

    //todo: what do to with password??
    private String Password;


    public User(UUID id, String email, Collection<Rule> rules, String username, LocalDateTime lastLoginDate, LocalDateTime credentialExpirationDate, LocalDateTime accountExpirationDate, boolean enabled, boolean expired, boolean credentialsExpired, String password) {
        super();
        super.ID = id;
        Email = email;
        Rules = rules;
        Username = username;
        LastLoginDate = lastLoginDate;
        CredentialExpirationDate = credentialExpirationDate;
        AccountExpirationDate = accountExpirationDate;
        Enabled = enabled;
        Expired = expired;
        CredentialsExpired = credentialsExpired;
        Password = password;
    }

    protected User(String username, String email, Collection<Rule> rules, String password) {
        super();
        Username = username;
        Email = email;
        Rules = rules;
        Password = password;
    }

    protected User(String username, String name, String surname, String email, Collection<Rule> rules, String password){
        super();
        Username = username;
        Email = email;
        Rules = rules;
        Password = password;
        Name = name;
        Surname = surname;
    }

    protected User addRole(Rule rule){
        Rules.add(rule);
        return this;
    }

    protected User deleteRole(Rule rule){
        Rules.remove(rule);
        return this;
    }

    //@Override
    public Collection<Rule> getAuthorities() {
        return getRules();
    }

    //@Override
    public boolean isAccountNonExpired() {
        return Expired;
    }

    //@Override
    public boolean isAccountNonLocked() {
        return !super.Locked;
    }

    //@Override
    public boolean isCredentialsNonExpired() {
        return CredentialsExpired;
    }

    @Override
    public void eraseCredentials() {
        Password = null;
    }


}
