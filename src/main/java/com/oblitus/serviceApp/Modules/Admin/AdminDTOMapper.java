package com.oblitus.serviceApp.Modules.Admin;

import com.oblitus.serviceApp.Modules.Admin.DTOs.RoleMapper;
import com.oblitus.serviceApp.Modules.Admin.DTOs.UserMapper;
import org.springframework.stereotype.Service;

@Service
public final class AdminDTOMapper {
    private static final RoleMapper ROLE_MAPPER = new RoleMapper();
    private static final UserMapper USER_MAPPER = new UserMapper(ROLE_MAPPER);

    private static AdminDTOMapper adminDTOMapper;

    public static AdminDTOMapper getInstance(){
        if(adminDTOMapper == null){
            adminDTOMapper = new AdminDTOMapper();
        }
        return adminDTOMapper;
    }
}
