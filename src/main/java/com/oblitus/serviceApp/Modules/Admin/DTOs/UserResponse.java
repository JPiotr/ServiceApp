package com.oblitus.serviceApp.Modules.Admin.DTOs;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.oblitus.serviceApp.Abstracts.BaseResponse;
import com.oblitus.serviceApp.Modules.BaseModule.DTOs.FileResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.Collection;


@EqualsAndHashCode(callSuper = true)
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse extends MyProfileResponse {
    private Collection<RuleDTO> rules;
    private boolean isEnabled;
    private boolean isExpired;
    private boolean isCredentialsExpired;


}


