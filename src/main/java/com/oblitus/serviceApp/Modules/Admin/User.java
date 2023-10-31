package com.oblitus.serviceApp.Modules.Admin;

import com.oblitus.serviceApp.Abstracts.EntityBase;
import com.oblitus.serviceApp.Security.Token;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
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
    @OneToMany(targetEntity = Token.class)
    private Collection<Token> tokens;

    @Setter
    @Getter
    private boolean enabled;

    @Getter
    private boolean expired;

    @Getter
    private boolean credentialsExpired;

    @Getter
    @Setter
    @Column(nullable = false)
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
        this.expired = false;
        this.surname = surname;
        this.locked = false;
        this.enabled = true;
        this.accountExpirationDate = LocalDateTime.now().plusMonths(3);
        this.credentialExpirationDate = LocalDateTime.now().plusMonths(3);
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
//    @Override
//    public Collection<Rule> getAuthorities() {
//        return getRules();
//    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRules();
    }

    @Override
    public boolean isAccountNonExpired() {
        return !expired;
    }
    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return !credentialsExpired;
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
