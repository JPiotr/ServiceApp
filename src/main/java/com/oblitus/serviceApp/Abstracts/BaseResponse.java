package com.oblitus.serviceApp.Abstracts;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class BaseResponse {
    private UUID uuid;
    private long id;
    private LocalDateTime creationDate;
    private LocalDateTime lastModificationDate;

}
