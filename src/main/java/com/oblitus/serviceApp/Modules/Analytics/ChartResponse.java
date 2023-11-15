package com.oblitus.serviceApp.Modules.Analytics;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Collection;

@Data
public class ChartResponse {
    private String name;
    private Collection<?> headers;
    private Collection<Double> data;
    private LocalDateTime analiseDateTime;

    public ChartResponse(String name, Collection<?> headers, Collection<Double> data) {
        this.name = name;
        this.headers = headers;
        this.data = data;
        analiseDateTime = LocalDateTime.now();
    }
}
