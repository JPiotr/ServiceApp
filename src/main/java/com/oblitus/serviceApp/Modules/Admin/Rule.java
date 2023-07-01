package com.oblitus.serviceApp.Modules.Admin;

import com.oblitus.serviceApp.Abstracts.EntityBase;
import com.oblitus.serviceApp.Modules.EModule;
import com.oblitus.serviceApp.Modules.Module;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;
//import org.springframework.security.core.GrantedAuthority;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
@Entity
@Table(name = "rules")
public class Rule extends EntityBase{ //implements GrantedAuthority {

    @OneToMany(targetEntity = Module.class)
    private List<Module> Modules;
    private String Name;
    private ERule ruleType;
    protected Rule(ERule roleType, List<Module> modules) {
        super();
        Name = roleType.toString();
        Modules = modules;
        this.ruleType = roleType;
    }

    //@Override
    public String getAuthority() {
        return ruleType.toString();
    }

}
