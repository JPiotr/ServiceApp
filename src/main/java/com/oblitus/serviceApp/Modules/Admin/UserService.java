package com.oblitus.serviceApp.Modules.Admin;

import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class UserService implements UserDetailsService {
    private final UserRepository userRepo;
    private final RuleService ruleService;
     public User getUser(UUID id) {
         var opt = userRepo.findById(id);
         if(opt.isPresent()){
             return opt.get();
         }
        throw new EntityNotFoundException();
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
    public User updateUser(UUID id, String username, String email, String password, String name, String surname) {
         User user = getUser(id);
         if(email != null){
             user.setEmail(email);
             user.setLastModificationDate();
         }
         if(username != null){
             user.setUsername(username);
             user.setLastModificationDate();
         }
         if(password != null){
             user.setPassword(password);
             user.setLastModificationDate();
         }
        if(name != null){
            user.setName(name);
            user.setLastModificationDate();
        }
        if(surname != null){
            user.setSurname(surname);
            user.setLastModificationDate();
        }
         return userRepo.save(user);
    }
    public boolean deleteUser(UUID id) {
         User user = getUser(id);
         userRepo.delete(user);
         return true;
    }
    public User addRuleToUser(UUID userID, String name) {
        var rule = ruleService.getRule(name);
        return userRepo.save(getUser(userID).addRole(rule));
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return getUser(username).orElse(null);
    }
    public User disconnectRuleFromUser(UUID userID, String name) {
        var rule = ruleService.getRule(name);
        return userRepo.save(getUser(userID).deleteRole(rule));
    }
    public User changeUserEnabled(UUID userId) {
         var user = getUser(userId);
         user.setEnabled(!user.isEnabled());
         return userRepo.save(user);
    }
}
