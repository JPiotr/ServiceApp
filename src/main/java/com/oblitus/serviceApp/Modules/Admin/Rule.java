package com.oblitus.serviceApp.Modules.Admin;

import com.oblitus.serviceApp.Abstracts.EntityBase;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
@Entity
@Table(name = "rules")
public class Rule extends EntityBase implements GrantedAuthority {

    @ElementCollection
    private List<EModule> Modules;
    private String Name;
    private ERule ruleType;
    //fixme: not List of EModule just list of ModuleBase!
    protected Rule(ERule roleType, List<EModule> modules) {
        super();
        Name = roleType.toString();
        Modules = modules;
        this.ruleType = roleType;
    }

    @Override
    public String getAuthority() {
        return ruleType.toString();
    }
}
