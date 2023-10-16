package com.oblitus.serviceApp.Modules.Service;

import com.oblitus.serviceApp.Modules.Admin.UserService;
import com.oblitus.serviceApp.Modules.Service.DTOs.ActivityDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ActivityService {
    private final ActivityRepository repository;
    private final UserService userService;
    private final TicketService ticketService;

    public Optional<Activity> getActivity(UUID id){ return repository.findById(id);}

    public List<Activity> getAllActivity(){ return repository.findAll();}

    public Activity addActivity(ActivityDTO activity){

        return repository.save(new Activity(
                activity.className(),
                activity.fieldName(),
                activity.newValue(),
                activity.oldValue(),
                activity.activityType(),
                userService.getUser(activity.userId()).get(),
                ticketService.getTicket(activity.ticketID()).get()
        ));
    }

    public boolean deleteActivity(UUID id){
        Optional<Activity> opt = repository.findById(id);
        if(opt.isEmpty()){
            return false;
        }
        repository.delete(opt.get());
        return true;
    }

}
