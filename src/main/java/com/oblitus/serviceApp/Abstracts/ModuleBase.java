package com.oblitus.serviceApp.Abstracts;

import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
public abstract class ModuleBase {
    public String Name;
    public List<ModuleElement> Elements;

}
