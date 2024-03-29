package com.oblitus.serviceApp.Modules.Analytics;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Collection;

@Data
public class ChartResponse {
    private String name;
    private Collection<?> headers;
    private Collection<Double> data;
    private Collection<ChartResponse> dataSets;
    private String description;
    private LocalDateTime analiseDateTime;
    private String type;
    private String analiseType;

    public ChartResponse(String name, Collection<?> headers, Collection<Double> data, String description, String type, String analiseType) {
        this.name = name;
        this.headers = headers;
        this.data = data;
        analiseDateTime = LocalDateTime.now();
        this.description = description;
        this.type = type;
        this.analiseType = analiseType;
    }

    public ChartResponse(String name, Collection<?> headers, String description, String type, String analiseType,Collection<ChartResponse> dataSets) {
        this.name = name;
        this.headers = headers;
        this.dataSets = dataSets;
        this.description = description;
        this.type = type;
        this.analiseType = analiseType;
        analiseDateTime = LocalDateTime.now();
    }
}
