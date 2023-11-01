package com.oblitus.serviceApp.Modules.Admin.DTOs;

public record PasswordChangeDTO(
        String password,
        String newPassword,
        String newPasswordConfirmation
) {
}
