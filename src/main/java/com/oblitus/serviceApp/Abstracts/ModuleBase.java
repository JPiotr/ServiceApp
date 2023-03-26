package com.oblitus.serviceApp.Abstracts;

import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
public abstract class ModuleBase extends EntityBase {
    public String moduleName;
    public List<ModuleElement> Elements;

}
