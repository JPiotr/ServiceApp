package com.oblitus.serviceApp.Modules.Admin.DAOs;

import com.oblitus.serviceApp.Abstracts.DAO;
import com.oblitus.serviceApp.Modules.Admin.DTOs.RuleDTO;
import com.oblitus.serviceApp.Modules.Admin.DTOs.RuleMapper;
import com.oblitus.serviceApp.Modules.Admin.RuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public final class RuleDAO implements DAO<RuleDTO, RuleDTO> {
    private final RuleService ruleService;
    private final RuleMapper ruleMapper;

    @Override
    public Optional<RuleDTO> get(UUID id) {
        return Optional.of(
                ruleMapper.apply(
                        ruleService.getRule(id)
                )
        );
    }

    @Override
    public List<RuleDTO> getAll() {
        return ruleService.getAllRoles()
                .stream()
                .map(ruleMapper)
                .collect(Collectors.toList()
                );
    }

    @Override
    public RuleDTO save(RuleDTO ruleDTO) {
        return ruleMapper.apply(
                ruleService.addRule(ruleDTO.name(),ruleDTO.modules())
        );
    }

    @Override
    public RuleDTO update(RuleDTO ruleDTO) {
        return null;
    }

    @Override
    public boolean delete(RuleDTO ruleDTO) {
        return false;
    }

    @Override
    public boolean delete(UUID id) {
        return false;
    }
}

