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
public class TaskService {
    private final TaskRepository repository;
    private final UserService userService;

    public Optional<Task> getTask(UUID id){
        return repository.findById(id);
    }

    public Task addTask(String title, String description, UUID userID){
        var opt = userService.getUser(userID);
        return opt.map(user -> repository.save(new Task(title, description, user)))
                .orElseGet(() -> repository.save(new Task(title, description, null)));
    }

    public boolean deleteTask(UUID id){
        var opt = repository.findById(id);
        if(opt.isEmpty()){
            return false;
        }
        repository.delete(opt.get());
        return true;
    }

    public List<Task> getAllTasks(){
        return repository.findAll();
    }

    public Task updateTask(UUID id, String title, String description){
        var opt = repository.findById(id);
        if(opt.isEmpty()){
            return null;
        }
        if(title != null){
            opt.get().setTitle(title);
        }
        if(description != null){
            opt.get().setDescription(description);
        }
        return repository.save(opt.get());
    }

}
