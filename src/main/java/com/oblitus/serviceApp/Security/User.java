package com.oblitus.serviceApp.Security;

import com.oblitus.serviceApp.Abstracts.EntityBase;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Table(name = "Users")
public class User extends EntityBase {
    public String Email;
    private String password;

}
