package com.oblitus.serviceApp.Modules.Admin.DAOs;

import com.oblitus.serviceApp.Modules.Admin.*;
import com.oblitus.serviceApp.Modules.Admin.DTOs.RuleDTO;
import com.oblitus.serviceApp.Modules.Admin.DTOs.RuleMapper;
import com.oblitus.serviceApp.Modules.EModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RuleDAOTest {

    @Mock
    private static RuleService ruleService;
    private static RuleMapper ruleMapper = new RuleMapper();
    @InjectMocks
    private static RuleDAO ruleDAO;

    private static RuleDTO ruleDTO;
    private static Rule rule;

    @BeforeEach
    void setUp() {
        ruleDAO = new RuleDAO(ruleService,ruleMapper);

        List<EModule> modules = new ArrayList<>();
        modules.add(EModule.BASE_MODULE);
        //rule = new Rule(ERule.USER,modules);
        ruleDTO = ruleMapper.apply(rule);
    }

    @Test
    @Disabled
    void get() {
        //given
        //given(ruleService.getRule(any(UUID.class))).willReturn(rule);
        //when
        ruleDAO.get(ruleDTO.id());
        //then
        verify(ruleService).getRule(rule.getID());
    }

    @Test
    @Disabled
    void getAll() {
        //given

        //when

        //then
    }

    @Test
    @Disabled
    void save() {
        //given

        //when

        //then
    }

    @Test
    @Disabled
    void cannotUpdate() {
        //given

        //when
        RuleDTO res = ruleDAO.update(ruleDTO);
        //then
        assertNull(res);
    }

    @Test
    @Disabled
    void cannotDelete() {
        //given

        //when
        boolean res = ruleDAO.delete(ruleDTO);
        //then
        assertFalse(res);
    }
}