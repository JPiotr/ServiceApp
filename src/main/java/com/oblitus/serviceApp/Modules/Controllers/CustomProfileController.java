package com.oblitus.serviceApp.Modules.Controllers;

import com.oblitus.serviceApp.Common.Response;
import com.oblitus.serviceApp.Modules.Admin.DTOs.ChangeProfileDetailsDTO;
import com.oblitus.serviceApp.Modules.Admin.DTOs.PasswordChangeDTO;
import com.oblitus.serviceApp.Modules.Admin.DTOs.SetPasswordDTO;
import com.oblitus.serviceApp.Modules.Admin.Exceptions.NewPasswordMismatchException;
import com.oblitus.serviceApp.Modules.Admin.Exceptions.PasswordNotMatchException;
import com.oblitus.serviceApp.Modules.Admin.Exceptions.PasswordSettingSessionExpiredException;
import com.oblitus.serviceApp.Modules.MappersWrapper;
import com.oblitus.serviceApp.Modules.ModulesWrapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/profile")
public class CustomProfileController {
    private final ModulesWrapper modulesWrapper;
    private final MappersWrapper mappersWrapper;

    //todo 2 factor authentication with email
    @GetMapping
    public ResponseEntity<Response> getMyProfile(Principal principal) {
        if (checkPrincipal(principal)) {
            String userName = principal.getName();
            try {
                var user = modulesWrapper.adminModule.getAdminDAO().getUserService().getUserByUserName(userName);
                return ResponseEntity.ok(
                        Response.builder()
                                .timestamp(LocalDateTime.now())
                                .message("Profile got.")
                                .data(Map.of("myProfile", mappersWrapper.myProfileMapper.apply(user)
                                        )
                                )
                                .statusCode(HttpStatus.OK.value())
                                .status(HttpStatus.OK)
                                .build()
                );
            } catch (EntityNotFoundException exception) {
                return ResponseEntity.ok(
                        Response.builder()
                                .timestamp(LocalDateTime.now())
                                .message("User " + userName + " not found.")
                                .data(Map.of("myProfile", " "))
                                .statusCode(HttpStatus.NOT_FOUND.value())
                                .status(HttpStatus.NOT_FOUND)
                                .reason("There is no Entity with this Username!")
                                .devMessage(exception.getMessage())
                                .build());
            }
        }
        return ResponseEntity.ok(
                Response.builder()
                        .timestamp(LocalDateTime.now())
                        .message("User not logged in.")
                        .data(Map.of("myProfile", " "))
                        .statusCode(HttpStatus.UNAUTHORIZED.value())
                        .status(HttpStatus.UNAUTHORIZED)
                        .reason("You must log in to get Your profile details")
                        .build());
    }

    @Deprecated
    @GetMapping("/{userName}")
    public ResponseEntity<Response> getProfile(@PathVariable @Validated String userName, Principal principal) {
        try {
            var user = modulesWrapper.adminModule.getAdminDAO().getUserService().getUserByUserName(userName);
            if (user.isPublicProfile() || checkPrincipal(principal)) {
                return ResponseEntity.ok(
                        Response.builder()
                                .timestamp(LocalDateTime.now())
                                .message("Profile got.")
                                .data(Map.of("profile", mappersWrapper.profileMapper.apply(user)
                                        )
                                )
                                .statusCode(HttpStatus.OK.value())
                                .status(HttpStatus.OK)
                                .build()
                );
            }
            return ResponseEntity.ok(
                    Response.builder()
                            .timestamp(LocalDateTime.now())
                            .message("User " + userName + " profile is private.")
                            .data(Map.of("profile", " "))
                            .statusCode(HttpStatus.UNAUTHORIZED.value())
                            .status(HttpStatus.UNAUTHORIZED)
                            .reason("You must log in to display this profile!")
                            .build());
        } catch (EntityNotFoundException exception) {
            return ResponseEntity.ok(
                    Response.builder()
                            .timestamp(LocalDateTime.now())
                            .message("User " + userName + " not found.")
                            .data(Map.of("profile", " "))
                            .statusCode(HttpStatus.NOT_FOUND.value())
                            .status(HttpStatus.NOT_FOUND)
                            .reason("There is no Profile with this Username!")
                            .devMessage(exception.getMessage())
                            .build());
        }
    }

    @PostMapping("/change-password")
    public ResponseEntity<Response> changePassword(@RequestBody @Validated PasswordChangeDTO passwordChangeDTO, Principal principal) {
        if (checkPrincipal(principal)) {
            String userName = principal.getName();
            try {
                return ResponseEntity.ok(
                        Response.builder()
                                .timestamp(LocalDateTime.now())
                                .message("Password changed.")
                                .data(Map.of("myProfile", mappersWrapper.myProfileMapper.apply(
                                                modulesWrapper.adminModule.getAdminDAO().getUserService().changeUserPassword(userName, passwordChangeDTO))
                                        )
                                )
                                .statusCode(HttpStatus.OK.value())
                                .status(HttpStatus.OK)
                                .build()
                );
            } catch (PasswordNotMatchException e) {
                return ResponseEntity.ok(
                        Response.builder()
                                .timestamp(LocalDateTime.now())
                                .message("Password not match.")
                                .data(Map.of("myProfile", " "))
                                .statusCode(HttpStatus.EXPECTATION_FAILED.value())
                                .status(HttpStatus.EXPECTATION_FAILED)
                                .reason("Actual password not match with database password!")
                                .build());
            } catch (NewPasswordMismatchException e) {
                return ResponseEntity.ok(
                        Response.builder()
                                .timestamp(LocalDateTime.now())
                                .message("New password mismatch")
                                .data(Map.of("myProfile", " "))
                                .statusCode(HttpStatus.NOT_ACCEPTABLE.value())
                                .status(HttpStatus.NOT_ACCEPTABLE)
                                .reason("New password confirmation not match new password!")
                                .build());
            } catch (EntityNotFoundException exception) {
                return ResponseEntity.ok(
                        Response.builder()
                                .timestamp(LocalDateTime.now())
                                .message("User " + userName + " not found.")
                                .data(Map.of("myProfile", " "))
                                .statusCode(HttpStatus.NOT_FOUND.value())
                                .status(HttpStatus.NOT_FOUND)
                                .reason("There is no Entity with this Username!")
                                .devMessage(exception.getMessage())
                                .build());
            }
        }
        return ResponseEntity.ok(
                Response.builder()
                        .timestamp(LocalDateTime.now())
                        .message("User not logged in.")
                        .data(Map.of("myProfile", " "))
                        .statusCode(HttpStatus.UNAUTHORIZED.value())
                        .status(HttpStatus.UNAUTHORIZED)
                        .reason("You must log in to change Your password")
                        .build());
    }

    @PostMapping("/change-details")
    public ResponseEntity<Response> changeProfileDetails(@RequestBody @Validated ChangeProfileDetailsDTO dto, Principal principal) {
        if (checkPrincipal(principal)) {
            String userName = principal.getName();
            try {
                return ResponseEntity.ok(
                        Response.builder()
                                .timestamp(LocalDateTime.now())
                                .message("Profile got.")
                                .data(Map.of("myProfile", mappersWrapper.myProfileMapper.apply(
                                        modulesWrapper.adminModule.getAdminDAO().getUserService()
                                                .changeProfileDetails(userName,dto))
                                        )
                                )
                                .statusCode(HttpStatus.OK.value())
                                .status(HttpStatus.OK)
                                .build()
                );
            } catch (EntityNotFoundException exception) {
                return ResponseEntity.ok(
                        Response.builder()
                                .timestamp(LocalDateTime.now())
                                .message("User " + userName + " not found.")
                                .data(Map.of("myProfile", " "))
                                .statusCode(HttpStatus.NOT_FOUND.value())
                                .status(HttpStatus.NOT_FOUND)
                                .reason("There is no Entity with this Username!")
                                .devMessage(exception.getMessage())
                                .build());
            }
        }
        return ResponseEntity.ok(
                Response.builder()
                        .timestamp(LocalDateTime.now())
                        .message("User not logged in.")
                        .data(Map.of("myProfile", " "))
                        .statusCode(HttpStatus.UNAUTHORIZED.value())
                        .status(HttpStatus.UNAUTHORIZED)
                        .reason("You must log in to get Your profile details")
                        .build());
    }

    @PostMapping("/set-password/{randomUuid}")
    public ResponseEntity<Response> changePassword(@RequestBody @Validated SetPasswordDTO setPasswordDTO, @PathVariable @Validated UUID randomUuid) {
        var usrservice = modulesWrapper.adminModule.getAdminDAO().getUserService();
        try {
            return ResponseEntity.ok(
                    Response.builder()
                            .timestamp(LocalDateTime.now())
                            .message("Password set.")
                            .data(Map.of("message", usrservice.setUserPassword(randomUuid,setPasswordDTO))
                            )
                            .statusCode(HttpStatus.OK.value())
                            .status(HttpStatus.OK)
                            .build()
            );
        } catch (NewPasswordMismatchException e) {
            return ResponseEntity.ok(
                    Response.builder()
                            .timestamp(LocalDateTime.now())
                            .message("New password mismatch")
                            .data(Map.of("message", " "))
                            .statusCode(HttpStatus.NOT_ACCEPTABLE.value())
                            .status(HttpStatus.NOT_ACCEPTABLE)
                            .reason("New password confirmation not match new password!")
                            .build());
        } catch (PasswordSettingSessionExpiredException e) {
            return ResponseEntity.ok(
                    Response.builder()
                            .timestamp(LocalDateTime.now())
                            .message("Session Expired!")
                            .data(Map.of("message", " "))
                            .statusCode(HttpStatus.NOT_FOUND.value())
                            .status(HttpStatus.NOT_FOUND)
                            .reason("Time for set password expired!")
                            .build());
        }
    }

    private boolean checkPrincipal(Principal principal) {
        return (principal != null && principal.getName() != null);
    }
}
