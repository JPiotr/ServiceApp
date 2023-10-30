package com.oblitus.serviceApp.Modules.Admin.DTOs;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.oblitus.serviceApp.Abstracts.BaseResponse;
import com.oblitus.serviceApp.Common.File.DTOs.FileResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;


@EqualsAndHashCode(callSuper = true)
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse extends BaseResponse {
    private String userName;
    private String email;
    private String name;
    private String surname;
    private Collection<RuleDTO> rules;
    private LocalDateTime lastLoginDate;
    private LocalDateTime credentialExpirationDate;
    private LocalDateTime accountExpirationDate;
    private boolean isEnabled;
    private boolean isExpired;
    private boolean isCredentialsExpired;
    private FileResponse file;
}


