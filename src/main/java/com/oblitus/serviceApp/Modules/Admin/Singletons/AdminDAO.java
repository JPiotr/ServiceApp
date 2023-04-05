package com.oblitus.serviceApp.Modules.Admin.Singletons;

import com.oblitus.serviceApp.Modules.Admin.DAOs.RuleDAO;
import com.oblitus.serviceApp.Modules.Admin.DAOs.UserDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public final class AdminDAO {
    private static RuleDAO RULE_DAO;
    private static UserDAO USER_DAO;

    private static AdminDAO adminDAO;

    public static AdminDAO getInstance(){
        if(adminDAO == null){
            adminDAO = new AdminDAO();
        }
        return adminDAO;
    }

    public static RuleDAO getRuleDao() {
        return RULE_DAO;
    }

    public static UserDAO getUserDao() {
        return USER_DAO;
    }
}
