package com.oblitus.serviceApp.Modules.Service.DTOs;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.oblitus.serviceApp.Abstracts.BaseResponse;
import com.oblitus.serviceApp.Modules.Admin.DTOs.ProfileResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ActivityResponse extends BaseResponse {
    private String className;
    private String fieldName;
    private String newValue;
    private String oldValue;
    private String activityType;
    private ProfileResponse creator;
    private UUID objectUuid;
}
