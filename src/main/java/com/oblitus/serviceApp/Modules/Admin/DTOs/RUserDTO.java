package com.oblitus.serviceApp.Modules.Admin.DTOs;

public record RUserDTO (
    String name,
    String surname,
    String email,
    String userName,

    String password
){}

