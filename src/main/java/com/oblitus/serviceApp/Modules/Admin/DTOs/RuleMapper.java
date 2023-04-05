package com.oblitus.serviceApp.Modules.Admin.DTOs;

import com.oblitus.serviceApp.Modules.Admin.Rule;
import org.springframework.stereotype.Service;

import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class RuleMapper implements Function<Rule, RuleDTO> {
    @Override
    public RuleDTO apply(Rule rule) {
        return new RuleDTO(
                rule.getID(),
                rule.getName(),
                rule.getModules().stream().map(Enum::toString).collect(Collectors.toList())
        );
    }
}
