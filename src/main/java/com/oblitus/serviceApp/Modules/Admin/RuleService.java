package com.oblitus.serviceApp.Modules.Admin;

import com.oblitus.serviceApp.Modules.EModule;
import com.oblitus.serviceApp.Modules.Module;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class RuleService {
    private final RuleRepository ruleRepository;

    public Rule addRule(String ruleName, List<Module> modules){
        return ruleRepository.save(
                new Rule(
                        ERule.valueOf(ruleName),
                        modules
                )
        );
    }
    public Rule getRule(UUID id){
        Optional<Rule> opt = ruleRepository.findById(id);
        return opt.orElse(null);
    }

    public Rule getRule(String name){
        Optional<Rule> opt = ruleRepository.findAll().stream().filter(r -> Objects.equals(r.getName(), name)).findFirst();
        return opt.orElse(null);
    }
    public List<Rule> getAllRoles(){
        return ruleRepository.findAll();
    }

}