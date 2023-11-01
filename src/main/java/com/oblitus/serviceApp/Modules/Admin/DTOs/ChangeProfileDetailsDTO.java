package com.oblitus.serviceApp.Modules.Admin.DTOs;

import com.oblitus.serviceApp.Abstracts.IModelDTO;

import java.util.UUID;

public record ChangeProfileDetailsDTO(String email, String name, String surname,
                                      UUID avatar, boolean changeVisibility) implements IModelDTO {
}
