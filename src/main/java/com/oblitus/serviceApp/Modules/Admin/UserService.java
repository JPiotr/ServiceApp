package com.oblitus.serviceApp.Modules.Admin;

import com.oblitus.serviceApp.Common.File.FileService;
import com.oblitus.serviceApp.Modules.Admin.DTOs.RuleDTO;
import com.oblitus.serviceApp.Modules.Admin.DTOs.RuleMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class UserService implements UserDetailsService {
    private final UserRepository userRepo;
    private final RuleService ruleService;
    private final RuleMapper ruleMapper;
    private final FileService fileService;
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
    public User addUser(String name, String email, Collection<Rule> rules, String password, String username, String surname, UUID fileId){

        User user = new User(username, name, surname, email, rules, password);
         if(rules == null || rules.isEmpty()){
             user = new User(username, name, surname, email, List.of(ruleService.getRule(ERule.USER.toString())), password);
         }
        if(fileId != null){
            var file = fileService.getFileById(fileId);
            file.setObjectId(user.getID());
            fileService.updateFile(file);
        }
        return userRepo.save(user);
    }
    public List<User> getAllUsers(){
        return userRepo.findAll();
    }
    public User updateUser(UUID id, String username, String email, String password, String name, String surname,
                           UUID fileId, Collection<RuleDTO> rules) {
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
         if(fileId != null){
            var file = fileService.getFileById(fileId);
            file.setObjectId(user.getID());
            fileService.updateFile(file);
        }
         if(rules != null && !rules.isEmpty()){
             user.setRules(null);
             user.setRules(
                     rules.stream()
                             .map(
                                     ruleDTO -> ruleService.getRule(ruleDTO.id()))
                             .collect(Collectors.toList()
                             )
             );
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
