package com.oblitus.serviceApp.Modules.Admin.DTOs;

import com.oblitus.serviceApp.Abstracts.IModelDTO;

public record RUserDTO(String name, String surname, String email, String userName,

                       String password) implements IModelDTO {
}

