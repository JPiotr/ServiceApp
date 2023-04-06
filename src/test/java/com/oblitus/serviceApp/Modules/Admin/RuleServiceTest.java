package com.oblitus.serviceApp.Modules.Admin;

import com.oblitus.serviceApp.Modules.Admin.DTOs.RuleDTO;
import com.oblitus.serviceApp.Modules.Admin.DTOs.RuleMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.rmi.server.RemoteStub;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RuleServiceTest {
    @Mock private RuleRepository ruleRepository;
    private static RuleService ruleService;
    private static Rule rule;
    private static RuleDTO ruleDTO;
    private static final RuleMapper ruleMapper = new RuleMapper();


    @BeforeEach
    void setUp() {
        List<EModule> modules = new ArrayList<>();
        modules.add(EModule.BASE_MODULE);
        rule = new Rule(ERule.USER,modules);
        ruleDTO = ruleMapper.apply(rule);

        ruleService = new RuleService(ruleRepository);
    }

    @Test
    void canTryAddRule() {
        //when
        ruleService.addRule(
                ruleDTO.name(),
                ruleDTO.modules());

        //then
        verify(ruleRepository).save(any());
    }

    @Test
    void canAddRule(){
        //when
        ruleService.addRule(ruleDTO.name(),ruleDTO.modules());

        //then
        ArgumentCaptor<Rule> ruleArgumentCaptor = ArgumentCaptor.forClass(Rule.class);
        verify(ruleRepository).save(ruleArgumentCaptor.capture());
        Rule capturedRule = ruleArgumentCaptor.getValue();
        assertThat(capturedRule).isEqualTo(rule);
    }

    @Test
    void canTryGetRule() {
        //when
        ruleService.getRule(ruleDTO.id());

        //then
        verify(ruleRepository).findById(any(UUID.class));
    }

    @Test
    void canGetRule() {
        //when
        ruleService.getRule(ruleDTO.id());

        //then
        ArgumentCaptor<UUID> uuidArgumentCaptor = ArgumentCaptor.forClass(UUID.class);
        verify(ruleRepository).findById(uuidArgumentCaptor.capture());
        UUID capturedUUID = uuidArgumentCaptor.getValue();
        assertThat(capturedUUID).isEqualTo(ruleDTO.id());
    }

    @Test
    void canGetAllRoles() {
        //when
        ruleService.getAllRoles();

        //then
        verify(ruleRepository).findAll();
    }
}