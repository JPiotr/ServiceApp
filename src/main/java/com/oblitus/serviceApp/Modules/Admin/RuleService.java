package com.oblitus.serviceApp.Modules.Admin;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class RuleService {
    private final RuleRepository ruleRepository;

    public Rule addRule(String ruleName, List<String> modules){
        return ruleRepository.save(
                new Rule(
                        ERule.valueOf(ruleName),
                        modules
                                .stream()
                                .map(EModule::valueOf)
                                .collect(Collectors.toList()
                                )
                )
        );
    }
    public Rule getRule(UUID id){
        Optional<Rule> opt = ruleRepository.findById(id);
        return opt.orElse(null);
    }
    public List<Rule> getAllRoles(){
        return ruleRepository.findAll();
    }

}