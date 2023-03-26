package com.oblitus.serviceApp.Security;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class User {
    public String Name;
    public String Email;
    private String password;

    public String getPassword(){
        return password;

    }
}
