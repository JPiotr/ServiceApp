package com.oblitus.serviceApp.Modules.Admin;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class UserService {
    private final UserRepository userRepo;
     public User getUser(UUID id){
        Optional<User> opt = userRepo.findById(id);
        return opt.orElse(null);
    }
    public User addUser(String name, String email, Collection<Rule> rules, String password){
        return userRepo.save(new User(name, email, rules, password));
    }
    public List<User> getAllUsers(){
        return userRepo.findAll();
    }
    public User updateUser(UUID id, String username, String email, String password){
         Optional<User> user = userRepo.findById(id);
         if(user.isEmpty()){
             return null;
         }
         if(email != null){
             user.get().setEmail(email);
         }
         if(username != null){
             user.get().setUsername(username);
         }
         if(password != null){
             user.get().setPassword(password);
         }
         return userRepo.save(user.get());
    }
    public boolean deleteUser(UUID id){
         Optional<User> user = userRepo.findById(id);
         if(user.isEmpty()){
             return false;
         }
        userRepo.delete(user.get());
         return true;
    }
}
