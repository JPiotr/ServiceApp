package com.oblitus.serviceApp.Modules.Service.DAOs;

import com.oblitus.serviceApp.Abstracts.DAO;
import com.oblitus.serviceApp.Modules.Service.ActivityService;
import com.oblitus.serviceApp.Modules.Service.DTOs.ActivityDTO;
import com.oblitus.serviceApp.Modules.Service.DTOs.ActivityResponse;
import com.oblitus.serviceApp.Modules.Service.DTOs.ActivityResponseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountLockedException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ActivityDAO implements DAO<ActivityResponse, ActivityDTO> {
    private final ActivityResponseMapper activityMapper;
    private final ActivityService service;
    @Override
    public Optional<ActivityResponse> get(UUID id) {
        var opt = service.getActivity(id);
        return opt.map(activityMapper);
    }

    @Override
    public List<ActivityResponse> getAll() {
        return service.getAllActivity()
                .stream()
                .map(activityMapper)
                .collect(Collectors.toList());
    }

    @Override
    public ActivityResponse save(ActivityDTO activityDTO) {
        return activityMapper.apply(
                service.addActivity(activityDTO)
        );
    }

    @Override
    public ActivityResponse update(ActivityDTO activityDTO) throws AccountLockedException {
        return null;
    }

    @Override
    public boolean delete(ActivityDTO activityDTO) {
        return false;
    }

    @Override
    public boolean delete(UUID id) {
        return false;
    }
}
