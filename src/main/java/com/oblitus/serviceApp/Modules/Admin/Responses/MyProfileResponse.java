package com.oblitus.serviceApp.Modules.Admin.Responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MyProfileResponse extends ProfileResponse {
    private String email;
    private LocalDateTime lastLoginDate;
    private LocalDateTime credentialExpirationDate;
    private LocalDateTime accountExpirationDate;
}
