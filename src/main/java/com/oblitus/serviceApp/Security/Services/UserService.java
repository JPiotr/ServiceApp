package com.oblitus.serviceApp.Security.Services;

import com.oblitus.serviceApp.Security.Entities.User;
import com.oblitus.serviceApp.Security.Repos.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepo;

    public User getUser(UUID id){
        Optional<User> opt = userRepo.findById(id);
        return opt.orElse(null);
    }

    public Iterable<User> getAllUsers(){
        return userRepo.findAll();
    }
}
