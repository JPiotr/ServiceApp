package com.oblitus.serviceApp.Modules.Admin.Singletons;

import com.oblitus.serviceApp.Modules.Admin.DAOs.RuleDAO;
import com.oblitus.serviceApp.Modules.Admin.DAOs.UserDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public final class AdminDAO {
    @Autowired
    private RuleDAO RULE_DAO;
    @Autowired
    private UserDAO USER_DAO;

    private static AdminDAO adminDAO;

    public static AdminDAO getInstance(){
        if(adminDAO == null){
            adminDAO = new AdminDAO();
        }
        return adminDAO;
    }

    public RuleDAO getRuleDao() {
        return RULE_DAO;
    }

    public UserDAO getUserDao() {
        return USER_DAO;
    }
}
