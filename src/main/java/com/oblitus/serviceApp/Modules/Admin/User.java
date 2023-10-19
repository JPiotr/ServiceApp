package com.oblitus.serviceApp.Modules.Admin;

import com.oblitus.serviceApp.Abstracts.EntityBase;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
@Entity
@Table(name = "users")
public class User extends EntityBase implements UserDetails, CredentialsContainer {
    @Column(nullable = false)
    private String Email;
    @ManyToMany(targetEntity = Rule.class, fetch = FetchType.EAGER)
    private Collection<Rule> Rules;
    @Column(nullable = false)
    private String Username;
    private String Name;
    private String Surname;
    private LocalDateTime LastLoginDate;
    private LocalDateTime CredentialExpirationDate;
    private LocalDateTime AccountExpirationDate;
    @Setter
    @Getter
    private boolean Enabled;
    @Getter
    private boolean Expired;
    @Getter
    private boolean CredentialsExpired;
    @Getter
    @Setter
    private String Password;

    public User(String id, String username, String password){
        super(id);
        Username = username;
        Password = password;
        Enabled = true;
        Locked = true;
        Expired = false;
        Email = username.toLowerCase() + "@srvctrack.root";
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
    public Collection<Rule> getAuthorities() {
        return getRules();
    }
    public boolean isAccountNonExpired() {
        return Expired;
    }
    public boolean isAccountNonLocked() {
        return !super.Locked;
    }
    public boolean isCredentialsNonExpired() {
        return CredentialsExpired;
    }
    @Override
    public void eraseCredentials() {
        Password = null;
    }
    public EntityBase getBase(){
        return (EntityBase) this;
    }
    @Override
    public String toString() {
        return getName()+ " " + getSurname();
    }
}
