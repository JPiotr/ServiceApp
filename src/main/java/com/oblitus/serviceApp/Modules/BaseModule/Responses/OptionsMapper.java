package com.oblitus.serviceApp.Modules.BaseModule.Responses;

import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class OptionsMapper implements Function<Enum<?>,OptionsResponse> {
    /**
     * @param s the function argument
     * @return
     */
    @Override
    public OptionsResponse apply(Enum e) {
        return new OptionsResponse(e.toString());
    }
}
