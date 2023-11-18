package com.oblitus.serviceApp.Modules.Admin.DTOs;

public record SetPasswordDTO(
        String newPassword,
        String newPasswordConfirmation
) {
}
