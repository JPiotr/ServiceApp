package com.oblitus.serviceApp.Modules.Project;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class FunctionalityService {
    private final FunctionalityRepository repository;
    private final TaskService taskService;

    public Optional<Functionality> getFunctionality(UUID id){
        return repository.findById(id);
    }

    public Functionality addFunctionality(String title){
        return repository.save(new Functionality(title,0,0,null));
    }

    public boolean deleteFunctionality(UUID id){
        var opt = repository.findById(id);
        if(opt.isEmpty()){
            return false;
        }
        repository.delete(opt.get());
        return true;
    }

    public List<Functionality> getAllFunctionalities(){
        return repository.findAll();
    }

    public Functionality updateFunctionality(UUID id, String title, int priority, int estimate){
        var opt = repository.findById(id);
        if(opt.isEmpty()){
            return null;
        }
        if(title != null){
            opt.get().setTitle(title);
        }
        if(estimate != 0){
            opt.get().setEstimate(estimate);
        }
        if(priority != 0){
            opt.get().setPriority(priority);
        }
        return repository.save(opt.get());
    }
}
