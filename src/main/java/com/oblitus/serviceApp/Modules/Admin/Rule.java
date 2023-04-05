package com.oblitus.serviceApp.Modules.Admin;

import com.oblitus.serviceApp.Abstracts.EntityBase;
import com.oblitus.serviceApp.Abstracts.ModuleBase;
import com.oblitus.serviceApp.Modules.Admin.EModule;
import com.oblitus.serviceApp.Modules.Admin.ERule;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class Role extends EntityBase implements GrantedAuthority {

    @ElementCollection
    public List<EModule> Modules;

    public String Name;
    private ERule roleType;
    //fixme: not List of EModule just list of ModuleBase!
    public Role(ERule roleType, List<EModule> modules) {
        super();
        Name = roleType.toString();
        Modules = modules;
        this.roleType = roleType;
    }

    @Override
    public String getAuthority() {
        return roleType.toString();
    }
}
