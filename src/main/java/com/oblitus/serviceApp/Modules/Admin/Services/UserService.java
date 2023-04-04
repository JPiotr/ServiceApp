package com.oblitus.serviceApp.Modules.Admin.Services;

import com.oblitus.serviceApp.Security.DataCrypt.Crypt;
import com.oblitus.serviceApp.Modules.Admin.Entities.Role;
import com.oblitus.serviceApp.Modules.Admin.Entities.User;
import com.oblitus.serviceApp.Modules.Admin.Repos.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepo;
    private static final Crypt crypt;

    static {
        try {
            crypt = new Crypt();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public User getUser(UUID id){
        Optional<User> opt = userRepo.findById(id);
        return opt.orElse(null);
    }

    public User addUser(String name, String email, Collection<Role> roles, String password) throws Exception {
        return userRepo.save(new User(name, email, roles, password));
//        return userRepo.save(new User(name, email, roles, crypt.cryptData(password)));
    }

    public User addUser(User user){
        return userRepo.save(user);
    }
    public Iterable<User> getAllUsers(){
        return userRepo.findAll();
    }
}
