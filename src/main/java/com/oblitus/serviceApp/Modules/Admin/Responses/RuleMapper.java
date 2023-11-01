package com.oblitus.serviceApp.Modules.Admin.Responses;

import com.oblitus.serviceApp.Modules.Admin.DTOs.RuleDTO;
import com.oblitus.serviceApp.Modules.Admin.Rule;
import org.springframework.stereotype.Service;

import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class RuleMapper implements Function<Rule, RuleDTO> { //todo: refactor this
    @Override
    public RuleDTO apply(Rule rule) {
        return new RuleDTO(
                rule.getUuid(),
                rule.getName(),
                rule.getModules()
        );
    }
}
