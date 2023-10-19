package com.oblitus.serviceApp.Modules.Admin;

import com.oblitus.serviceApp.Abstracts.EntityBase;
import com.oblitus.serviceApp.Modules.Module;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
@Entity
@Table(name = "rules")
public class Rule extends EntityBase implements GrantedAuthority {

    @ManyToMany(targetEntity = Module.class, fetch = FetchType.EAGER)
    private List<Module> Modules;
    private String Name;
    private ERule RuleType;
    protected Rule(ERule roleType, List<Module> modules) {
        super();
        Name = roleType.toString();
        Modules = modules;
        RuleType = roleType;
    }
    public Rule(String uuid, List<Module> modules, ERule ruleType) {
        super(uuid);
        Modules = modules;
        Name = ruleType.toString();
        RuleType = ruleType;
    }
    public String getAuthority() {
        return RuleType.toString();
    }

}
