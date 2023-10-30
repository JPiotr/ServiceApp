package com.oblitus.serviceApp.Modules.Admin.DTOs;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.oblitus.serviceApp.Abstracts.BaseResponse;
import com.oblitus.serviceApp.Common.File.DTOs.FileResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseUserResponse extends BaseResponse {
        private String userName;
        private String name;
        private String surname;
        private FileResponse photo;
}
