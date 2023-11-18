package com.oblitus.serviceApp.Modules.Admin.Singletons;

import com.oblitus.serviceApp.Modules.Admin.RuleService;
import com.oblitus.serviceApp.Modules.Admin.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public final class AdminDAO {

    private final RuleService RULE_SERVICE;

    private final UserService USER_SERVICE;

    public AdminDAO getInstance(){
        return this;
    }

    public RuleService getRuleService() {
        return RULE_SERVICE;
    }

    public UserService getUserService() {
        return USER_SERVICE;
    }
}
