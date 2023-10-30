package com.oblitus.serviceApp.Modules.Admin;

import com.oblitus.serviceApp.Abstracts.IService;
import com.oblitus.serviceApp.Modules.Admin.DTOs.RuleDTO;
import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class RuleService implements IService<Rule, RuleDTO> {
    private final RuleRepository ruleRepository;

    @Override
    public Rule get(RuleDTO dto) {
        Optional<Rule> opt = Optional.empty();
        if(dto.name() != null){
            opt = ruleRepository.findRuleByName(dto.name());
        }
        if(dto.id() != null){
            opt = ruleRepository.findById(dto.id());
        }
        if(opt.isPresent()){
            return opt.get();
        }
        throw new EntityNotFoundException();
    }

    @Override
    public Collection<Rule> getAll() {
        return ruleRepository.findAll();
    }

    @Override
    public Rule update(RuleDTO dto) {
        return null;
    }

    @Override
    public Rule add(RuleDTO dto) {
        return ruleRepository.save(
                new Rule(
                        ERule.getValue(dto.name()),
                        dto.modules()
                )
        );
    }

    @Override
    public boolean delete(RuleDTO dto) {
        return false;
    }


}