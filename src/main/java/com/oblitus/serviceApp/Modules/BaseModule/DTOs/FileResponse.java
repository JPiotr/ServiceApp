package com.oblitus.serviceApp.Modules.BaseModule.DTOs;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.oblitus.serviceApp.Abstracts.BaseResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.core.io.Resource;

import java.time.LocalDateTime;
import java.util.UUID;
@EqualsAndHashCode(callSuper = true)
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FileResponse extends BaseResponse {
        private UUID objectId;
        private String name;
        private String fileExtension;
        private String fileType;
        private String url;
        private String description;
}
