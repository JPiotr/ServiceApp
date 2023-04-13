package com.oblitus.serviceApp.Modules;

import com.oblitus.serviceApp.Abstracts.EntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//todo: ModuleDTO
@Entity
@Table(name="modules")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Module extends EntityBase {
    public String Name;
    public boolean Enabled;
    public EModule Type;


}
