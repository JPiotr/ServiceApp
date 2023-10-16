package com.oblitus.serviceApp.Modules.Admin;

import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountLockedException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class UserService implements UserDetailsService {
    private final UserRepository userRepo;
    private final RuleService ruleService;
     public Optional<User> getUser(UUID id){
        return userRepo.findById(id);
    }
     public Optional<User> getUser(String name){
        return Optional.ofNullable(userRepo.findAll().stream().filter(x-> Objects.equals(x.getUsername(), name)).toList().get(0));
    }
    public User addUser(String name, String email, Collection<Rule> rules, String password, String username, String surname){
         if(rules == null || rules.isEmpty()){
             return userRepo.save(new User(username, name, surname, email, List.of(ruleService.getRule(ERule.USER.toString())), password));
         }
        return userRepo.save(new User(username, name, surname, email, rules, password));
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
             user.get().setLastModificationDate();
         }
         if(username != null){
             user.get().setUsername(username);
             user.get().setLastModificationDate();
         }
         if(password != null){
             user.get().setPassword(password);
             user.get().setLastModificationDate();
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

    public User addRuleToUser(UUID userID, String name){
         var opt = getUser(userID);
         if(opt.isPresent()){
             var rule = ruleService.getRule(name);
             var user = opt.get().addRole(rule);
             return userRepo.save(user);
         }
         return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return getUser(username).orElse(null);
    }

    public User disconnectRuleFromUser(UUID userID, String name) {
        var opt = getUser(userID);
        if(opt.isPresent()){
            var rule = ruleService.getRule(name);
            var user = opt.get().deleteRole(rule);
            return userRepo.save(user);
        }
        return null;
    }
}
