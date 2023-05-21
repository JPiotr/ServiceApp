package com.oblitus.serviceApp.Modules.Project;

import com.oblitus.serviceApp.Abstracts.EntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

import java.util.Collection;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
@Entity
@Table(name = "functionalities")
public class Functionality extends EntityBase {
    private String Title;
    private int Priority;
    private int Estimate;
    @OneToMany(targetEntity = Task.class)
    private Collection<Task> Tasks;
}
