package com.oblitus.serviceApp.Modules.Service;

import com.oblitus.serviceApp.Abstracts.IService;
import com.oblitus.serviceApp.Modules.Service.DTOs.ActivityDTO;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ActivityService implements IService<Activity, ActivityDTO> {
    private final ActivityRepository repository;

    @Override
    public Activity get(ActivityDTO dto) {
        var opt = repository.findById(dto.id());
        if(opt.isPresent()){
            return opt.get();
        }
        throw new EntityNotFoundException();
    }

    @Override
    public Collection<Activity> getAll() {
        return repository.findAll();
    }

    @Override
    @Deprecated
    public Activity update(ActivityDTO dto) {
        return null;
    }

    @Override
    @Deprecated
    public Activity add(ActivityDTO dto) {
        return null;
    }

    @Override
    @Deprecated
    public boolean delete(ActivityDTO dto) {
        return false;
    }

    public Collection<Activity> getObjectActivities(UUID object){
        return repository.findAllByObjectActivity(object);
    }

}
