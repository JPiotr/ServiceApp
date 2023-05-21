package com.oblitus.serviceApp.Modules.Project;

import com.oblitus.serviceApp.Abstracts.EntityBase;
import com.oblitus.serviceApp.Modules.Admin.User;
import com.oblitus.serviceApp.Modules.Service.Comment;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.*;

import java.util.Collection;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
@Entity
@Table(name = "tasks")
public class Task extends EntityBase {
    private String Title;
    private String Description;
    private TaskState State;
    @OneToOne(targetEntity = User.class)
    private User User;
    @OneToMany(targetEntity = Comment.class)
    private Collection<Comment> Comments;
}
