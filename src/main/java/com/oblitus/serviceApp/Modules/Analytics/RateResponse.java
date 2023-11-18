package com.oblitus.serviceApp.Modules.Analytics;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RateResponse {
    private String name;
    private double val;
    private LocalDateTime analiseDateTime;
    private String description;
    private String type;
    private String analiseType;

    public RateResponse(String name, double rate) {
        this.name = name;
        this.val = rate;
        this.analiseDateTime = LocalDateTime.now();
    }

    public RateResponse(String name, String type, double value, String description, String analiseType){
        this.name = name;
        this.type = type;
        this.val = value;
        this.analiseDateTime = LocalDateTime.now();
        this.description = description;
        this.analiseType = analiseType;
    }
}
