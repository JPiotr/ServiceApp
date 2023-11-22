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

}
