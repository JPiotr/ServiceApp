package com.oblitus.serviceApp.Modules.Admin.Services;

import com.oblitus.serviceApp.Modules.Admin.EModule;
import com.oblitus.serviceApp.Modules.Admin.ERule;
import com.oblitus.serviceApp.Modules.Admin.Entities.Role;
import com.oblitus.serviceApp.Modules.Admin.Repos.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;
//todo: fix typo
@Service
@AllArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Role addRole(ERule ruleType, ArrayList<EModule> modules){
        return roleRepository.save(new Role(ruleType, modules));
    }

    public Role addRole(Role role){
        return roleRepository.save(role);
    }
    public Role getRule(UUID id){
        Optional<Role> opt = roleRepository.findById(id);
        return opt.orElse(null);
    }

    public Iterable<Role> getAllRoles(){
        return roleRepository.findAll();
    }

}
