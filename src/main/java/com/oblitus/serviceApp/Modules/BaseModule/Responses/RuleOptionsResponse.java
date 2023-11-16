package com.oblitus.serviceApp.Modules.BaseModule.Responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RuleOptionsResponse {
    private UUID uuid;
    private String key;
    private String value;

    public RuleOptionsResponse(UUID uuid, String key) {
        this.uuid = uuid;
        this.key = key;
        this.value = this.key.toLowerCase();
    }
}
