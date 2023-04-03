package com.oblitus.serviceApp.Modules.Admin.Services;

import com.oblitus.serviceApp.Modules.Admin.EModule;
import com.oblitus.serviceApp.Modules.Admin.Entities.Role;
import com.oblitus.serviceApp.Modules.Admin.Repos.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Role addRole(String name, ArrayList<EModule> modules){
        return roleRepository.save(new Role(name, modules));
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
