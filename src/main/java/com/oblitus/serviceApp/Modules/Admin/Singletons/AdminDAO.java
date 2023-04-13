package com.oblitus.serviceApp.Modules.Admin.Singletons;

import com.oblitus.serviceApp.Modules.Admin.DAOs.RuleDAO;
import com.oblitus.serviceApp.Modules.Admin.DAOs.UserDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public final class AdminDAO {

    private final RuleDAO RULE_DAO;

    private final UserDAO USER_DAO;

//    private final AdminDAO adminDAO;

    public AdminDAO getInstance(){
        return this;
    }

    public RuleDAO getRuleDao() {
        return RULE_DAO;
    }

    public UserDAO getUserDao() {
        return USER_DAO;
    }
}
