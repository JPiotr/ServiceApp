package com.oblitus.serviceApp.Security;

import com.oblitus.serviceApp.Abstracts.EntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Table(name = "Roles")
public class Role extends EntityBase {

}
