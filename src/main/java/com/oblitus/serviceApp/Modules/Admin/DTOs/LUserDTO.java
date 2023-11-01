package com.oblitus.serviceApp.Modules.Admin.DTOs;


import com.oblitus.serviceApp.Abstracts.IModelDTO;

public record LUserDTO(String userName, String password) implements IModelDTO {
}
