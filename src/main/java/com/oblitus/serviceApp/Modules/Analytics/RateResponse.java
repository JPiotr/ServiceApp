package com.oblitus.serviceApp.Modules.Analytics;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RateResponse {
    private String name;
    private double rate;
    private LocalDateTime analiseDateTime;

    public RateResponse(String name, double rate) {
        this.name = name;
        this.rate = rate;
        analiseDateTime = LocalDateTime.now();
    }
}
