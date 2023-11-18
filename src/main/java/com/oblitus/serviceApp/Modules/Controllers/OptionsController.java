package com.oblitus.serviceApp.Modules.Controllers;

import com.oblitus.serviceApp.Common.Response;
import com.oblitus.serviceApp.Modules.BaseModule.DTOs.NotificationDTO;
import com.oblitus.serviceApp.Modules.MappersWrapper;
import com.oblitus.serviceApp.Modules.ModulesWrapper;
import com.oblitus.serviceApp.Modules.Service.TicketPriority;
import com.oblitus.serviceApp.Modules.Service.TicketState;
import jakarta.persistence.EntityNotFoundException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

@RestController
@RequestMapping("/options")
@RequiredArgsConstructor
public class OptionsController {
    public final ModulesWrapper modulesWrapper;
    public final MappersWrapper mappersWrapper;

    @GetMapping("/rules")
    public ResponseEntity<Response> getRulesOptions(){
        return ResponseEntity.ok(
                Response.builder()
                        .timestamp(LocalDateTime.now())
                        .message("All rules options")
                        .data(Map.of("rulesOptions",modulesWrapper.adminModule.getAdminDAO().getRuleService().getAll()
                                .stream().map(mappersWrapper.ruleOptionsMapper).toList()))
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .build()
        );
    }
    @GetMapping("/state")
    public ResponseEntity<Response> getStateOptions(){
        return ResponseEntity.ok(
                Response.builder()
                        .timestamp(LocalDateTime.now())
                        .message("All state options")
                        .data(Map.of("stateOptions", Arrays.stream(TicketState.values()).toList()
                                .stream().map(mappersWrapper.optionsMapper).toList()))
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .build()
        );
    }
    @GetMapping("/priority")
    public ResponseEntity<Response> getPriorityOptions(){
        return ResponseEntity.ok(
                Response.builder()
                        .timestamp(LocalDateTime.now())
                        .message("All priority options")
                        .data(Map.of("priorityOptions", Arrays.stream(TicketPriority.values()).toList()
                                .stream().map(mappersWrapper.optionsMapper).toList()))
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .build()
        );
    }

    @GetMapping("/notifications")
    public ResponseEntity<Response> getNotifications(Principal principal){
        if(checkPrincipal(principal)){
            String userName = principal.getName();
            var user = modulesWrapper.adminModule.getAdminDAO().getUserService().getUserByUserName(userName);
            try{
                return ResponseEntity.ok(
                        Response.builder()
                                .timestamp(LocalDateTime.now())
                                .message("Notification user options")
                                .data(Map.of("notificationOptions", modulesWrapper.baseModule.getBaseDAO()
                                                        .getNotificationService()
                                                        .getAllUserNotificationOptions(user.getUuid())
                                                        .stream().map(mappersWrapper.notificationMapper).toList()
                                        )
                                )
                                .statusCode(HttpStatus.OK.value())
                                .status(HttpStatus.OK)
                                .build()
                );
            }catch (EntityNotFoundException e){
                return ResponseEntity.ok(
                        Response.builder()
                                .timestamp(LocalDateTime.now())
                                .message("User " + userName + " not found.")
                                .data(Map.of("notificationOptions", " "))
                                .statusCode(HttpStatus.NOT_FOUND.value())
                                .status(HttpStatus.NOT_FOUND)
                                .reason("There is no Entity with this Username!")
                                .devMessage(e.getMessage())
                                .build());
            }

        }
        return ResponseEntity.ok(
                Response.builder()
                        .timestamp(LocalDateTime.now())
                        .message("User not logged in.")
                        .data(Map.of("notificationOptions", " "))
                        .statusCode(HttpStatus.UNAUTHORIZED.value())
                        .status(HttpStatus.UNAUTHORIZED)
                        .reason("You must log in to get Your profile details")
                        .build());
    }

    @PostMapping("/notifications")
    public ResponseEntity<Response> updateNotifications(@RequestBody @Validated @NonNull Collection<NotificationDTO> dtos){
        return ResponseEntity.ok(
                Response.builder()
                        .timestamp(LocalDateTime.now())
                        .message("Notification user options updated!")
                        .data(Map.of("notificationOptions", modulesWrapper.baseModule.getBaseDAO()
                                        .getNotificationService()
                                        .updateAll(dtos)
                                        .stream().map(mappersWrapper.notificationMapper).toList()
                                )
                        )
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .build()
        );
    }

    private boolean checkPrincipal(Principal principal) {
        return (principal != null && principal.getName() != null);
    }

}
