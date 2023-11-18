package com.oblitus.serviceApp.Modules.BaseModule.Responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.oblitus.serviceApp.Abstracts.BaseResponse;
import com.oblitus.serviceApp.Modules.Admin.Responses.ProfileResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NotificationResponse extends BaseResponse {
    private ProfileResponse user;
    private Boolean app;
    private Boolean email;
    private String type;
}
