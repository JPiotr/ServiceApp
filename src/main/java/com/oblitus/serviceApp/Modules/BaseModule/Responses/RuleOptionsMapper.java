package com.oblitus.serviceApp.Modules.BaseModule.Responses;

import com.oblitus.serviceApp.Modules.Admin.Rule;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class RuleOptionsMapper implements Function<Rule,RuleOptionsResponse> {
    /**
     * @param rule Rule
     * @return new RuleOptionsResponse
     * @see Rule
     * @see RuleOptionsResponse
     */
    @Override
    public RuleOptionsResponse apply(Rule rule) {
        return new RuleOptionsResponse(rule.getUuid(),rule.getName());
    }
}
