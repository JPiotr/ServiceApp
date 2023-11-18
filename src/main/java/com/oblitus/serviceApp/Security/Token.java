package com.oblitus.serviceApp.Security;

import com.oblitus.serviceApp.Abstracts.EntityBase;
import com.oblitus.serviceApp.Modules.Admin.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "tokens")
@NoArgsConstructor
@AllArgsConstructor
public class Token extends EntityBase {
    @Column(unique = true)
    private String token;
    private TokenType tokenType = TokenType.BEARER;
    private boolean revoked;
    private boolean expired;
    @ManyToOne(targetEntity = User.class)
    private User user;
}
