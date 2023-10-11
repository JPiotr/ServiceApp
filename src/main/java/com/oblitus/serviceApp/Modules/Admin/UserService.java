package com.oblitus.serviceApp.Modules.Admin;

import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountLockedException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class UserService implements UserDetailsService {
    private final UserRepository userRepo;
     public Optional<User> getUser(UUID id){
        return userRepo.findById(id);
    }
     public Optional<User> getUser(String name){
        return Optional.ofNullable(userRepo.findAll().stream().filter(x-> Objects.equals(x.getUsername(), name)).toList().get(0));
    }
    public User addUser(String name, String email, Collection<Rule> rules, String password){
        return userRepo.save(new User(name, email, rules, password));
    }
    public List<User> getAllUsers(){
        return userRepo.findAll();
    }
    public User updateUser(UUID id, String username, String email, String password) throws AccountLockedException {
         Optional<User> user = userRepo.findById(id);
         if(user.isEmpty()){
             throw new EntityNotFoundException("User with id "+ id + "not found!");
         }
         if(!user.get().isAccountNonLocked()){
             throw new AccountLockedException("Account " + user.get().getUsername() + " is locked!");
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


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return getUser(username).orElse(null);
    }
}
