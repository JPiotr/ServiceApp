package com.oblitus.serviceApp.Modules.Project;

import com.oblitus.serviceApp.Modules.Admin.UserService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class ProjectService {
    private final ProjectRepository repository;
    private final UserService userService;

    public Optional<Project> getProject(UUID id){
        return repository.findById(id);
    }

    public Project addProject(String name, String description, UUID userID){
        var user = userService.getUser(userID);
        return repository.save(new Project(name, description, user));
    }

    public boolean deleteProject(UUID id){
        var opt = repository.findById(id);
        if(opt.isEmpty()){
            return false;
        }
        repository.delete(opt.get());
        return true;
    }

    public List<Project> getAllProjects(){
        return repository.findAll();
    }

    public Project updateProject(UUID id, String name, String description){
        var opt = repository.findById(id);
        if(opt.isEmpty()){
            return null;
        }
        if(name != null){
            opt.get().setName(name);
        }
        if(description != null){
            opt.get().setDescription(description);
        }
        return repository.save(opt.get());
    }
}
