package com.oblitus.serviceApp.Modules.Admin;

import com.oblitus.serviceApp.Abstracts.EntityBase;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.CredentialsContainer;
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
    private static long num = 0;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String username;

    private String name;
    private String surname;
    private LocalDateTime lastLoginDate;
    private LocalDateTime credentialExpirationDate;
    private LocalDateTime accountExpirationDate;

    @Setter
    @Getter
    private boolean enabled;

    @Getter
    private boolean expired;

    @Getter
    private boolean credentialsExpired;

    @Getter
    @Setter
    private String password;

    @ManyToMany(targetEntity = Rule.class, fetch = FetchType.EAGER)
    private Collection<Rule> rules;

    public User(String uuid, String username, String password){
        super(uuid);
        this.username = username;
        this.password = password;
        this.enabled = true;
        locked = true;
        expired = false;
        email = "srvctrack@gmail.com";
        setID(User.num);
        User.num += 1;
    }
    protected User(String username, String name, String surname, String email, Collection<Rule> rules, String password){
        super();
        this.username = username;
        this.email = email;
        this.rules = rules;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.enabled = true;
        setID(User.num);
        User.num += 1;
    }
    protected User addRole(Rule rule){
        rules.add(rule);
        return this;
    }
    protected User deleteRole(Rule rule){
        rules.remove(rule);
        return this;
    }
    public Collection<Rule> getAuthorities() {
        return getRules();
    }
    public boolean isAccountNonExpired() {
        return expired;
    }
    public boolean isAccountNonLocked() {
        return !locked;
    }
    public boolean isCredentialsNonExpired() {
        return credentialsExpired;
    }

    @Override
    public void eraseCredentials() {
        password = null;
    }
    public EntityBase getBase(){
        return (EntityBase) this;
    }

    @Override
    public String toString() {
        return getName()+ " " + getSurname();
    }
}
