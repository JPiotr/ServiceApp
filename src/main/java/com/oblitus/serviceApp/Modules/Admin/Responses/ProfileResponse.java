package com.oblitus.serviceApp.Modules.Admin.Responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.oblitus.serviceApp.Abstracts.BaseResponse;
import com.oblitus.serviceApp.Modules.BaseModule.DTOs.FileResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileResponse extends BaseResponse {
    private String userName;
    private String name;
    private String surname;
    private FileResponse avatar;
}
