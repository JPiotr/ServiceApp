package com.oblitus.serviceApp.Modules.BaseModule.Responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OptionsResponse {
    private String key;
    private String value;

    public OptionsResponse(String key) {
        this.key = key;
        this.value = key;
    }
}
