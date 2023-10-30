package com.oblitus.serviceApp.Modules.Admin;

import com.oblitus.serviceApp.Abstracts.EntityBase;
import com.oblitus.serviceApp.Modules.Module;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
@Entity
@Table(name = "rules")
public class Rule extends EntityBase implements GrantedAuthority {

    @ManyToMany(targetEntity = Module.class, fetch = FetchType.EAGER)
    private List<Module> modules;
    private String name;
    private ERule ruleType;
    protected Rule(ERule ruleType, List<Module> modules) {
        super();
        name = ruleType.toString();
        this.modules = modules;
        this.ruleType = ruleType;
    }
    public Rule(String uuid, List<Module> modules, ERule ruleType) {
        super(uuid);
        this.modules = modules;
        name = ruleType.toString();
        this.ruleType = ruleType;
    }
    public String getAuthority() {
        return ruleType.toString();
    }

}
