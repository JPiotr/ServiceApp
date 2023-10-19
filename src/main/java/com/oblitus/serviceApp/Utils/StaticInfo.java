package com.oblitus.serviceApp.Utils;

import com.oblitus.serviceApp.Modules.Admin.ERule;
import com.oblitus.serviceApp.Modules.Admin.Rule;
import com.oblitus.serviceApp.Modules.Admin.User;
import com.oblitus.serviceApp.Modules.EModule;
import com.oblitus.serviceApp.Modules.Module;
import com.oblitus.serviceApp.Modules.Service.Client;

import java.util.List;

public final class StaticInfo {
    public static List<Module> Modules =
            List.of(
             new Module("00000000-0000-0000-0001-000000000001", EModule.ADMIN_MODULE.toString(),     true ,EModule.ADMIN_MODULE),
             new Module("00000000-0000-0000-0001-000000000002", EModule.BASE_MODULE.toString(),      true ,EModule.BASE_MODULE),
             new Module("00000000-0000-0000-0001-000000000003", EModule.PROJECTS_MODULE.toString(),  false,EModule.PROJECTS_MODULE),
             new Module("00000000-0000-0000-0001-000000000004", EModule.SERVICE_MODULE.toString(),   true, EModule.SERVICE_MODULE)
            );

    public static List<Rule> PredefinedRules =
            List.of(
                    new Rule(
                            "00000000-0000-0000-0002-000000000001",
                            List.of(StaticInfo.Modules.get(0),
                                    StaticInfo.Modules.get(1)
                            ),
                            ERule.ADMIN
                    ),
                    new Rule(
                            "00000000-0000-0000-0002-000000000002",
                            List.of(StaticInfo.Modules.get(1)),
                            ERule.USER
                    ),
                    new Rule(
                            "00000000-0000-0000-0002-000000000003",
                            List.of(StaticInfo.Modules.get(1),
                                    StaticInfo.Modules.get(3)
                            ),
                            ERule.CLIENT
                    ),
                    new Rule(
                            "00000000-0000-0000-0002-000000000004",
                            List.of(StaticInfo.Modules.get(1),
                                    StaticInfo.Modules.get(3)
                            ),
                            ERule.SERVICE
                    )
            );
    public static String SuperUserPasswd = "root"; //todo: getting variable from properties
    public static User SuperUser = new User(
            "00000000-0000-0000-0003-000000000001",
            "System",
            "");

    public static Client Internal = new Client(
            "00000000-0000-0000-0004-000000000001",
            "Internal",
            SuperUser
    );

}
